package hr.tvz.susac.java.web.flight.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.tvz.susac.java.web.flight.util.constraint.EmailUniqueConstraint;
import hr.tvz.susac.java.web.flight.util.constraint.UsernameUniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO
{
    @UsernameUniqueConstraint
    @NotNull(message = "Username is required!")
    @Size(min = 6, max = 55, message = "Username must be between 6 and 50 characters!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username only accepts alphanumeric letters without any white space!")
    @JsonProperty(value = "username")
    private String username;

    @NotNull(message = "Password is required!")
    @Size(min = 8, max = 80, message = "Password must be between 8 and 80 characters!")
    @Pattern(regexp = "[A-Za-z0-9]+$", message = "Password only accepts alphanumeric letters!")
    @JsonProperty(value = "password")
    private String password;

    @EmailUniqueConstraint
    @NotNull(message = "E-mail address is required!")
    @Email(message = "E-mail address is in incorrect format!")
    @JsonProperty(value = "email")
    private String email;
}
