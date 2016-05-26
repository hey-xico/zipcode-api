package br.com.chico.addressapi.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * @author Francisco Almeida
 * @since 25/05/2016
 */
@Constraint(validatedBy = {})
@Documented
@ReportAsSingleViolation
@Pattern(regexp="[0-9]{8}")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cep {

    String message() default "CEP inválido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}