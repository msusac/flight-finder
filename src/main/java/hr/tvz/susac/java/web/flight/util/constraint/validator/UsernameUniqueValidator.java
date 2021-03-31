package hr.tvz.susac.java.web.flight.util.constraint.validator;

import hr.tvz.susac.java.web.flight.model.User;
import hr.tvz.susac.java.web.flight.repository.UserRepository;
import hr.tvz.susac.java.web.flight.util.constraint.UsernameUniqueConstraint;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@AllArgsConstructor
public class UsernameUniqueValidator implements ConstraintValidator<UsernameUniqueConstraint, String>
{
    private final UserRepository userRepository;

    @Override
    public void initialize(UsernameUniqueConstraint username) { }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext ctx)
    {
        User user = userRepository.findOneByUsername(username).orElse(null);

        if (Objects.isNull(user)) return true;

        return false;
    }
}
