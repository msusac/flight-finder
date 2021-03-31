package hr.tvz.susac.java.web.flight.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO
{
    @NotNull(message = "Username is required!")
    @JsonProperty(value = "username")
    private String username;

    @NotNull(message = "Password is required!")
    @JsonProperty(value = "password")
    private String password;
}
