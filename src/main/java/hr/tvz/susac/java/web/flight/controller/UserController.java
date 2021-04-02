package hr.tvz.susac.java.web.flight.controller;

import hr.tvz.susac.java.web.flight.dto.user.LoginDTO;
import hr.tvz.susac.java.web.flight.dto.user.RegisterDTO;
import hr.tvz.susac.java.web.flight.dto.user.UserDTO;
import hr.tvz.susac.java.web.flight.util.exception.BadRequestException;
import hr.tvz.susac.java.web.flight.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController
{
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser()
    {
        UserDTO userDTO = userService.findCurrentUser();
        log.info("User info:" + userDTO);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/airtoken")
    public ResponseEntity<?> getAirApiToken(@RequestHeader("authorization") String jwt)
    {
        UserDTO userDTO = userService.findCurrentUserAirToken(jwt.split(" ")[1]);
        log.info("User Air Api Token info: " + userDTO);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO, Errors errors)
    {
        if(errors.hasErrors()) throw new BadRequestException(errors);

        UserDTO userDTO = userService.login(loginDTO);
        log.info("User Login Info: " + userDTO);

        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO registerDTO, Errors errors)
    {
        if(errors.hasErrors()) throw new BadRequestException(errors);

        UserDTO userDTO = userService.register(registerDTO);
        log.info("User Created: " + userDTO);

        return ResponseEntity.ok("Registration successful! You can now login!");
    }
}
