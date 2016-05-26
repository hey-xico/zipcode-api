package br.com.chico.addressapi.exception;

/**
 * @author Francisco Almeida
 * @since 26/05/2016
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String s) {
        super(s);
    }
}
