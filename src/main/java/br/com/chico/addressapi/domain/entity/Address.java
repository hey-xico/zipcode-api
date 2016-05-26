package br.com.chico.addressapi.domain.entity;

import br.com.chico.addressapi.domain.validation.Cep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Francisco Almeida on 25/05/2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String street;

    @NotNull
    private Long number;

    @NotNull
    @Cep
    private String zipcode;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private String district;

    private String complement;

}
