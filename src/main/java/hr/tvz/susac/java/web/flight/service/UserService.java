package hr.tvz.susac.java.web.flight.service;

import hr.tvz.susac.java.web.flight.dto.user.LoginDTO;
import hr.tvz.susac.java.web.flight.dto.user.RegisterDTO;
import hr.tvz.susac.java.web.flight.dto.user.UserDTO;

public interface UserService
{
    UserDTO findCurrentUserAirToken(String jwt);

    UserDTO findCurrentUser();

    UserDTO login(LoginDTO loginDTO);

    UserDTO register(RegisterDTO registerDTO);
}
