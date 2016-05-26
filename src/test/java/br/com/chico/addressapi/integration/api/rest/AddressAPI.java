package br.com.chico.addressapi.integration.api.rest;

import br.com.chico.addressapi.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Before
    public void _init() {
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
                .andDo(print())
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
                .andDo(print())
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
                .andDo(print())
                .andExpect(jsonPath("$..message").value("CEP inválido"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void given_ValidId_ShouldReturnAnAddress() throws Exception {
        mockMvc.perform(
                get(APP_PATH + "?id=1")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    public void given_InvalidId_ShouldReturnErrorMessage() throws Exception {
        mockMvc.perform(
                get(APP_PATH + "?id=100")
                        .accept(APPLICATION_JSON_VALUE)
                        .contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$..message").value("Endereço não encontrado para o ID::100"))
                .andExpect(status().isOk());
    }
}
