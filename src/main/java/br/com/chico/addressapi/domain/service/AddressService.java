package br.com.chico.addressapi.domain.service;

import br.com.chico.addressapi.domain.entity.Address;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
public interface AddressService {
    Address getByZipcode(String zipcode);
}
