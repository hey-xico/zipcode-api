package br.com.chico.addressapi.domain.service;

import br.com.chico.addressapi.domain.entity.Address;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
public interface AddressService {

    /**
     * Get an {$link Address} by its zipcode
     *
     * @param zipcode {$link Address}'s zipcode
     * @return {$link Address}
     */
    Address getByZipcode(String zipcode);

    /**
     * Get an {$link Address} by its id
     *
     * @param id {$link Address}'s id
     * @return {$link Address}
     */
    Address findOne(Long id);

    /**
     * Delete an {$link Address} by id.
     *
     * @param id {$link Address}'s id to be removed
     */
    void delete(Long id);

    /**
     * Save or Update {$link Address} object.
     *
     * @param address {$link Address} object
     * @return a {$link Address} with a valid ID
     */
    Address save(Address address);

}
