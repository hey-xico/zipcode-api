package br.com.chico.addressapi.api.rest;

import br.com.chico.addressapi.domain.service.AddressService;
import br.com.chico.addressapi.exception.AddressNotFoundException;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
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
        Optional<Address> propertyDTO = this.addressService.getByZipcode(zipcode);
        return ResponseEntity.ok(propertyDTO.orElseThrow(() -> new AddressNotFoundException("CEP inválido")));
    }

}
