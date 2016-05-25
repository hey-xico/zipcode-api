package br.com.chico.addressapi.exception;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}
