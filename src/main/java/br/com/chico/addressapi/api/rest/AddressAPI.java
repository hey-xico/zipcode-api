package br.com.chico.addressapi.api.rest;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
@RestController
@RequestMapping(name = "/address", produces = APPLICATION_JSON_UTF8_VALUE)
public class AddressAPI {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/{zipcode}", method = RequestMethod.GET)
    public ResponseEntity<Address> get(@PathVariable String zipcode) {
        Address propertyDTO = this.addressService.getByZipcode(zipcode);
        return ResponseEntity.ok(propertyDTO);
    }

}
