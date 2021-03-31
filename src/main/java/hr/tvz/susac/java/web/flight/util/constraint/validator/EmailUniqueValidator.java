package hr.tvz.susac.java.web.flight.util.constraint.validator;

import hr.tvz.susac.java.web.flight.model.User;
import hr.tvz.susac.java.web.flight.repository.UserRepository;
import hr.tvz.susac.java.web.flight.util.constraint.EmailUniqueConstraint;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@AllArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUniqueConstraint, String>
{
    private final UserRepository userRepository;

    @Override
    public void initialize(EmailUniqueConstraint email) { }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext ctx)
    {
        User user = userRepository.findOneByEmail(email).orElse(null);

        if (Objects.isNull(user)) return true;

        return false;
    }
}