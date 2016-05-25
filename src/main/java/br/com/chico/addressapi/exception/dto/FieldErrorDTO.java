package br.com.chico.addressapi.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
@Data
@AllArgsConstructor
public class FieldErrorDTO {
    private String fieldName;
    private String fieldError;
}
