package br.com.chico.addressapi.domain.service;

import java.util.Optional;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
public interface AddressService {
    Optional<br.com.chico.addressapi.domain.entity.Address> getByZipcode(String zipcode);
}
