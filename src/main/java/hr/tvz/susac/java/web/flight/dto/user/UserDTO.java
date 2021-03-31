package hr.tvz.susac.java.web.flight.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "jwtToken")
    private String jwtToken;

    @JsonProperty(value = "airToken")
    private String airToken;
}
