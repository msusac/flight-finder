package hr.tvz.susac.java.web.flight.util.constraint.validator;

import hr.tvz.susac.java.web.flight.util.constraint.CurrencyCodeConstraint;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@AllArgsConstructor
public class CurrencyCodeValidator implements ConstraintValidator<CurrencyCodeConstraint, String> {
    @Override
    public void initialize(CurrencyCodeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext constraintValidatorContext) {
        if(Arrays.asList("", "USD", "EUR", "HRK").contains(currencyCode))
            return true;

        return false;
    }
}
