package br.com.chico.addressapi.domain.service.impl;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.repository.AddressRepository;
import br.com.chico.addressapi.domain.service.AddressService;
import br.com.chico.addressapi.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    /**
     * Return a {@link Address} by its zipcode.
     *
     * @param zipcode A valid {@link Address} zipcode
     * @return {@link Address} for the given zipcode
     */
    @Override
    public Address getByZipcode(String zipcode) {
        StringBuilder zipcodeSB = new StringBuilder(zipcode);
        Optional<Address> address = Optional.ofNullable(addressRepository.findByZipcode(zipcodeSB.toString()));


        while (!address.isPresent() && !zipcodeSB.toString().equals("00000000")) {
            addZeroAtTheEnd(zipcodeSB);
            address = Optional.ofNullable(addressRepository.findByZipcode(zipcodeSB.toString()));
        }
        return address.orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado para o CEP", HttpStatus.NOT_FOUND));

    }

    /**
     * Get an {$link Address} by its id
     *
     * @param id {$link Address}'s id
     * @return {$link Address}
     */
    @Override
    public Address findOne(Long id) {
        return Optional
                .ofNullable(this.addressRepository.findOne(id))
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado para o ID::" + id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete an {$link Address} by id.
     *
     * @param id {$link Address}'s id to be removed
     */
    @Override
    public void delete(Long id) {
        this.addressRepository.delete(Optional
                .ofNullable(this.addressRepository.findOne(id))
                .orElseThrow(()->new AddressNotFoundException("Endereço não encontrado para o ID::" + id, HttpStatus.NOT_FOUND)));
    }

    /**
     * Save or Update {$link Address} object.
     *
     * @param address {$link Address} object
     * @return a {$link Address} with a valid ID
     */
    @Override
    public Address save(Address address) {
        Objects.requireNonNull(address);
        return this.addressRepository.save(address);
    }

    /**
     * Replace the last value, different of zero, by zero
     */
    private void addZeroAtTheEnd(StringBuilder zipCode) {
        for (int i = zipCode.length() - 1; i >= 0; i--) {
            if (zipCode.charAt(i) != '0') {
                zipCode.replace(i, i + 1, "0");
                break;
            }
        }
    }
}
