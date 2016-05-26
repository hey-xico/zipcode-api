package br.com.chico.addressapi.unit.domain.service.impl;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.repository.AddressRepository;
import br.com.chico.addressapi.domain.service.impl.AddressServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest {

    private static final String VALID_ZIPCODE =  "06410020";
    private static final String VALID_ZIPCODE_REPLACED_BY_ZER0 =  "06410000";

    @InjectMocks
    private AddressServiceImpl target;

    @Mock
    private AddressRepository mockAddressRepository;

    @Before
    public void thosewerethebestdaysofmylifeeeeeee() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getByZipcode_WithAValidParam_MustReturnAnAddress(){
        //Given
        Address addressFixture = new Address(null, "Rua Turmalina", 246L, "06420010", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findByCep(VALID_ZIPCODE)).thenReturn(Optional.of(addressFixture));
        //When

        Address result = target.getByZipcode(VALID_ZIPCODE);

        Mockito.verify(mockAddressRepository, times(1)).findByCep(VALID_ZIPCODE);

        assertNotNull(result);
        assertThat(result.getCity(), equalTo("Barueri"));
    }

    @Test
    public void test_getByZipcode_WhenReplaceByZeroOcurred_MustReturnAnAddress(){
        //Given
        Address addressFixture = new Address(null, "Rua Turmalina", 246L, "06420000", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findByCep(VALID_ZIPCODE_REPLACED_BY_ZER0)).thenReturn(Optional.of(addressFixture));
        //When

        Address result = target.getByZipcode(VALID_ZIPCODE);

        Mockito.verify(mockAddressRepository, times(1)).findByCep(VALID_ZIPCODE_REPLACED_BY_ZER0);

        assertNotNull(result);
        assertThat(result.getCity(), equalTo("Barueri"));
    }


    @Test
    public void test_getByZipcode_WhenReplaceTheZipCode(){
        //Given
        Address addressFixture = new Address(null, "Rua Turmalina", 246L, "06420000", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findByCep(VALID_ZIPCODE_REPLACED_BY_ZER0)).thenReturn(Optional.of(addressFixture));
        //When

        Address result = target.getByZipcode(VALID_ZIPCODE);

        Mockito.verify(mockAddressRepository, times(1)).findByCep(VALID_ZIPCODE_REPLACED_BY_ZER0);

        assertNotNull(result.get());
        assertThat(result.get().getCity(), equalTo("Barueri"));
    }
}