package br.com.chico.addressapi.unit.domain.service.impl;

import br.com.chico.addressapi.domain.entity.Address;
import br.com.chico.addressapi.domain.repository.AddressRepository;
import br.com.chico.addressapi.domain.service.impl.AddressServiceImpl;
import br.com.chico.addressapi.exception.AddressNotFoundException;
import br.com.chico.addressapi.exception.ValidationException;
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

    private static final String VALID_ZIPCODE = "06410020";
    private static final String VALID_ZIPCODE_REPLACED_BY_ZER0 = "06410000";
    private static final String VALID_ZIPCODE_OUT_AREA = "06417111";

    @InjectMocks
    private AddressServiceImpl target;

    @Mock
    private AddressRepository mockAddressRepository;

    @Before
    public void thosewerethebestdaysofmylifeeeeeee() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getByZipcode_WithAValidParam_MustReturnAnAddress() {
        //Given
        Address addressFixture = new Address(null, "Rua Turmalina", 246L, "06420010", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findByZipcode(VALID_ZIPCODE)).thenReturn(addressFixture);
        //When

        Address result = target.getByZipcode(VALID_ZIPCODE);

        //Then
        Mockito.verify(mockAddressRepository, times(1)).findByZipcode(VALID_ZIPCODE);

        assertNotNull(result);
        assertThat(result.getCity(), equalTo("Barueri"));
    }

    @Test
    public void test_getByZipcode_WhenReplaceByZeroOccurred_MustReturnAnAddress() {
        //Given
        Address addressFixture = new Address(null, "Rua Turmalina", 246L, "06420000", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findByZipcode(VALID_ZIPCODE_REPLACED_BY_ZER0)).thenReturn(addressFixture);
        //When

        Address result = target.getByZipcode(VALID_ZIPCODE);

        //Then
        Mockito.verify(mockAddressRepository, times(1)).findByZipcode(VALID_ZIPCODE_REPLACED_BY_ZER0);

        assertNotNull(result);
        assertThat(result.getCity(), equalTo("Barueri"));
    }

    @Test(expected = AddressNotFoundException.class)
    public void test_getByZipcode_WhenReplaceTheSuffixIsNotSufficient_ThrowException() {
        //When
        Address result = target.getByZipcode(VALID_ZIPCODE_OUT_AREA);
    }

    @Test
    public void test_FindOne_WithValidID_MustSucceed() {
        //Given
        final Long ID = 1l;
        Address addressFixture = new Address(ID, "Rua Turmalina", 246L, "06420000", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findOne(ID)).thenReturn(addressFixture);
        //When
        Address result = target.findOne(ID);

        //Then
        Mockito.verify(mockAddressRepository, times(1)).findOne(ID);

        assertNotNull(result);
        assertThat(result.getId(), equalTo(ID));
    }


    @Test(expected = AddressNotFoundException.class)
    public void test_FindOne_WhenNotFound_ThrownAddressNotFoundException() {
        //Given
        final Long ID = 1l;

        Mockito.when(mockAddressRepository.findOne(ID)).thenReturn(null);
        //When
        Address result = target.findOne(ID);

    }


    @Test
    public void test_Delete_WithValidID_MustSucceed() {
        //Given
        final Long ID = 1l;
        Address addressFixture = new Address(ID, "Rua Turmalina", 246L, "06420000", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.findOne(ID)).thenReturn(addressFixture);
        //When
        target.delete(ID);
        //Then
        Mockito.verify(mockAddressRepository, times(1)).delete(addressFixture);

    }

    @Test(expected = AddressNotFoundException.class)
    public void test_Delete_WhenNotFound_ThrownAddressNotFoundException() {
        //Given
        final Long ID = 1l;
        //When
        target.delete(ID);
        //Then
    }


    @Test
    public void test_save_WithValidAddress_MustSucceed() {
        //Given
        Address addressFixture = new Address(null, "Rua Turmalina", 246L, "06420000", "Barueri", "Sao Paulo", null, null);
        Mockito.when(mockAddressRepository.save(addressFixture)).thenReturn(addressFixture);
        //When
        Address result = target.save(addressFixture);
        //Then
        Mockito.verify(mockAddressRepository, times(1)).save(addressFixture);
        assertNotNull(result);
    }

    @Test(expected = ValidationException.class)
    public void test_save_WithNullAddress_ThrowValidationException() {
        //When
        Address result = target.save(null);

    }

}