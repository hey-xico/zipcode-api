package br.com.chico.addressapi.domain.service.impl;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.repository.AddressRepository;
import br.com.chico.addressapi.domain.service.AddressService;
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

    @Override
    public Optional<Address> getByZipcode(String zipcode) {
        Optional<Address> address = addressRepository.findByCep(zipcode);
        return address.isPresent() ? address : getByZipcode(addZeroAtTheEnd(new StringBuilder(zipcode)).toString());
    }

    private StringBuilder addZeroAtTheEnd(StringBuilder zipCode) {
        for (int i = zipCode.length() - 1; i >= 0; i--) {
            if (zipCode.charAt(i) != '0') {
                zipCode.replace(i, i + 1, "0");
                break;
            }
        }
        return zipCode;
    }
}
