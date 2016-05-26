package br.com.chico.addressapi.domain.repository;

import br.com.chico.addressapi.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
public interface AddressRepository extends JpaRepository<Address, Long>{
    Address findByCep(String zipcode);
}
