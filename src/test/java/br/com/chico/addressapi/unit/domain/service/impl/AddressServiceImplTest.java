package br.com.chico.addressapi.unit.domain.service.impl;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.repository.AddressRepository;
import br.com.chico.addressapi.domain.service.impl.AddressServiceImpl;
import br.com.chico.addressapi.exception.AddressNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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
    private static final String VALID_ZIPCODE_OUT_AREA =  "06417111";

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
        Mockito.when(mockAddressRepository.findByZipcode(VALID_ZIPCODE)).thenReturn(addressFixture);
        //When

        Address result = target.getByZipcode(VALID_ZIPCODE);

        Mockito.verify(mockAddressRepository, times(1)).findByZipcode(VALID_ZIPCODE);

        assertNotNull(result);
        assertThat(result.getCity(), equalTo("Barueri"));
    }

    @Test
    public void test_getByZipcode_WhenReplaceByZeroOccurred_MustReturnAnAddress(){
        //Given
        Address addressFixture = new Address(null, "Rua Turmalina", 246L, "06420000", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findByZipcode(VALID_ZIPCODE_REPLACED_BY_ZER0)).thenReturn(addressFixture);
        //When

        Address result = target.getByZipcode(VALID_ZIPCODE);

        Mockito.verify(mockAddressRepository, times(1)).findByZipcode(VALID_ZIPCODE_REPLACED_BY_ZER0);

        assertNotNull(result);
        assertThat(result.getCity(), equalTo("Barueri"));
    }

    @Test(expected = AddressNotFoundException.class)
    public void test_getByZipcode_WhenReplaceTheSuffixIsNotSufficient_ThrowException(){
        //When
        Address result = target.getByZipcode(VALID_ZIPCODE_OUT_AREA);
    }
}