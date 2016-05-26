package br.com.chico.addressapi.domain.service.impl;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.repository.AddressRepository;
import br.com.chico.addressapi.domain.service.AddressService;
import br.com.chico.addressapi.exception.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * When an address wasn't found by the given zipcode we will replace its suffix {@link https://goo.gl/TErTqE} to find a close address
     *
     * @param zipcode A valid {@link Address} zipcode
     * @return {@link Address} for the given zipcode
     */
    @Override
    public Address getByZipcode(String zipcode) {
        StringBuilder zipcodeSB = new StringBuilder(zipcode);
        Optional<Address> address = addressRepository.findByCep(zipcodeSB.toString());
        while (address == null && zipcodeSB.subSequence(5, 7) != "000") {
            addZeroAtTheEnd(zipcodeSB);
            address = addressRepository.findByCep(zipcodeSB.toString());
        }
        return address.orElseThrow(()->new AddressNotFoundException("Endereço não encontrado para o CEP"));

    }

    private void addZeroAtTheEnd(StringBuilder zipCode) {
        for (int i = zipCode.length() - 1; i >= 0; i--) {
            if (zipCode.charAt(i) != '0') {
                zipCode.replace(i, i + 1, "0");
                break;
            }
        }
    }
}
