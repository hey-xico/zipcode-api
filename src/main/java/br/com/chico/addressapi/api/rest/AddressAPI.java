package br.com.chico.addressapi.api.rest;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.service.AddressService;
import br.com.chico.addressapi.domain.validation.Cep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
@RestController
@RequestMapping(value = "/address", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
@Validated
public class AddressAPI {

    @Autowired
    private AddressService addressService;

    /**
     *
     * Get an {$link Address} by its zipcode
     *
     * @param zipcode {$link Address}'s zipcode
     * @return {$link Address}
     *
     * */
    @RequestMapping(value = "/{zipcode}", method = RequestMethod.GET)
    public ResponseEntity<Address> getByZipCode(@Valid @Cep @PathVariable String zipcode) {
        Address propertyDTO = this.addressService.getByZipcode(zipcode);
        return ResponseEntity.ok(propertyDTO);
    }


    /**
     *
     * Get an {$link Address} by its id
     *
     * @param id {$link Address}'s id
     * @return {$link Address}
     *
     * */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Address> getAddressById(@RequestParam Long id) {
        Address addressRequest = addressService.findOne(id);
        return new ResponseEntity<>(addressRequest, HttpStatus.OK);
    }


    /**
     *
     * Delete an {$link Address} by id.
     *
     * @param id {$link Address}'s id to be removed
     * @return {$HttpStatus} 204
     *
     * */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAddressById(@PathVariable Long id) {
        addressService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     *
     * Save a new {$link Address} object.
     *
     * @param address {$link Address} object
     * @return a new {$link Address} with a valid ID
     *
     * */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Address> saveAddress(@Valid @RequestBody Address address) {
        Address savedAddress = addressService.save(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    /**
     *
     * Update an existent {$link Address}.
     *
     * @param address {$link Address} object to be updated
     * @return the {$link Address} updated
     *
     * */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Address> editAddress(@Valid @RequestBody Address address) {
        addressService.save(address);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }


}
