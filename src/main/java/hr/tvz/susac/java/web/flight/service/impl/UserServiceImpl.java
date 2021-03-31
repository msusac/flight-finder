package hr.tvz.susac.java.web.flight.service.impl;

import hr.tvz.susac.java.web.flight.dto.user.LoginDTO;
import hr.tvz.susac.java.web.flight.dto.user.RegisterDTO;
import hr.tvz.susac.java.web.flight.dto.user.UserDTO;
import hr.tvz.susac.java.web.flight.util.exception.UnauthorizedRequestException;
import hr.tvz.susac.java.web.flight.model.User;
import hr.tvz.susac.java.web.flight.repository.UserRepository;
import hr.tvz.susac.java.web.flight.security.jwt.JwtTokenProvider;
import hr.tvz.susac.java.web.flight.service.UserService;
import hr.tvz.susac.java.web.flight.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    private final ConverterUtil<User, UserDTO> converterUtil;
    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO findCurrentUserAirToken(String jwt)
    {
        UserDTO userDTO = converterUtil.convertToDTO(getCurrentUser());
        userDTO.setAirToken(jwtTokenProvider.extractAirApiTokenFromJwt(jwt));

        return userDTO;
    }

    @Override
    public UserDTO findCurrentUser()
    {
        return converterUtil.convertToDTO(getCurrentUser());
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String token = jwtTokenProvider.generateToken(authentication);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(loginDTO.getUsername());
        userDTO.setJwtToken(token);

        return userDTO;
    }

    @Override
    public UserDTO register(RegisterDTO registerDTO)
    {
        User user = convertRegisterDTOToUser(registerDTO);
        user = userRepository.save(user);

        return converterUtil.convertToDTO(user);
    }

    private User convertRegisterDTOToUser(RegisterDTO registerDTO)
    {
        User user = modelMapper.map(registerDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }

    public User getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findOneByUsername(authentication.getName()).orElseThrow(() -> {
            throw new UnauthorizedRequestException("Unauthorized Request!");
        });
    }
}
