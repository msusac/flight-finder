package hr.tvz.susac.java.web.flight.util.constraint;

import hr.tvz.susac.java.web.flight.util.constraint.validator.CurrencyCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyCodeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrencyCodeConstraint
{
    String message() default "Unacceptable Currency Code. Use only USD, EUR, HRK or leave it empty!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
