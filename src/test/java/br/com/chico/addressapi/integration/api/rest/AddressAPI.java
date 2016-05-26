package br.com.chico.addressapi.integration.api.rest;

import br.com.chico.addressapi.Application;
import br.com.chico.addressapi.domain.entity.Address;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Francisco Almeida
 * @since 26/05/2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AddressAPI {

    private static final String VALID_ZIPCODE = "01415000";
    private static final String APP_PATH = "/address";


    @Autowired
    private WebApplicationContext webAppConfiguration;

    private MockMvc mockMvc;

    private ObjectMapper mapper;
    @Before
    public void _init() {
        this.mapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webAppConfiguration).build();
    }

    @Test
    public void GivenAValidZipCodeShouldReturnAnAddress() throws Exception {
        //GIVEN

        //Execute
        mockMvc.perform(
                get(APP_PATH + "/" + VALID_ZIPCODE)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.zipcode").value("01415000"))
                .andExpect(status().isOk());
    }

    @Test
    public void given_ValidZipCode_ButNotExists_ReturnCloseAddress() throws Exception {
        //GIVEN

        //Execute
        mockMvc.perform(
                get(APP_PATH + "/06401011")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.zipcode").value("06401010"))
                .andExpect(status().isOk());
    }

    @Test
    public void given_InvalidZipCode_ReturnAnError() throws Exception {
        //GIVEN

        //Execute
        mockMvc.perform(
                get(APP_PATH + "/0640101")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$..message").value("CEP inválido"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void given_ValidId_ShouldReturnAnAddress() throws Exception {
        mockMvc.perform(
                get(APP_PATH + "?id=1")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    public void given_InvalidId_ShouldReturnErrorMessage() throws Exception {
        mockMvc.perform(
                get(APP_PATH + "?id=100")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$..message").value("Endereço não encontrado para o ID::100"))
                .andExpect(status().isNotFound());
    }



    @Test
    public void given_ValidId_ShouldRemoveAnAddress() throws Exception {
        mockMvc.perform(
                delete(APP_PATH + "/1")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void given_InvalidId_WhenTryToRemoveIt_ReturnErrorMessage() throws Exception {
        mockMvc.perform(
                delete(APP_PATH + "/100")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$..message").value("Endereço não encontrado para o ID::100"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void given_ValidAddress_WhenSave_ReturnStatusCreated() throws Exception {

        Address addressFixture = new Address(null, "Street of Tests", 1000L, "01010101", "TestingTown", "TestingState", null, null);
        mockMvc.perform(
                post(APP_PATH)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(addressFixture)))
                .andDo(print())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(status().isCreated());
    }

    @Test
    public void given_AddressWithInvalidZipCode_WhenSave_ReturnErrorMessage() throws Exception {

        Address addressFixture = new Address(null, "Street of Tests", 1000L, "0101000101", "TestingTown", "TestingState", null, null);
        mockMvc.perform(
                post(APP_PATH)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(addressFixture)))
                .andDo(print())
                .andExpect(jsonPath("$.fieldErrors").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors[0].fieldError").value("CEP inválido"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void given_AddressWithoudRequiredProperty_WhenSave_ReturnErrorMessage() throws Exception {

        Address addressFixture = new Address(null, null, 1000L, "06410020", "TestingTown", "TestingState", null, null);
        mockMvc.perform(
                post(APP_PATH)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(addressFixture)))
                .andDo(print())
                .andExpect(jsonPath("$.fieldErrors").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors[0].fieldError").value("may not be null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void given_ExistingAddress_WhenUpdate_ReturnSuccess() throws Exception {

        //Given
        Address addressFixture = new Address(null, "Street of Tests", 1000L, "01010101", "TestingTown", "TestingState", null, null);
        MvcResult result = mockMvc.perform(
                post(APP_PATH)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(addressFixture)))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(status().isCreated()).andReturn();

        Address addressFromContentResponde = getAddressFromContentResponde(result.getResponse().getContentAsString());

        addressFromContentResponde.setComplement("Test Update");
        //Do Update
        mockMvc.perform(
                put(APP_PATH)
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(addressFromContentResponde)))
                .andExpect(jsonPath("$.complement").value(addressFromContentResponde.getComplement()))
                .andExpect(status().isOk());


    }

    private Address getAddressFromContentResponde(String contentAsString) throws IOException {
        return mapper.readValue(contentAsString, Address.class);
    }


}
