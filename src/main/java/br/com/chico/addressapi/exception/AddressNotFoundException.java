package br.com.chico.addressapi.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
public class AddressNotFoundException extends RuntimeException {
    private HttpStatus httpStatus;
    public AddressNotFoundException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
