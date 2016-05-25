package br.com.chico.addressapi.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericErrorDTO {
    private String message;
}
