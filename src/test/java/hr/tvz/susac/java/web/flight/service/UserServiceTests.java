package hr.tvz.susac.java.web.flight.service;

import hr.tvz.susac.java.web.flight.dto.user.LoginDTO;
import hr.tvz.susac.java.web.flight.dto.user.RegisterDTO;
import hr.tvz.susac.java.web.flight.dto.user.UserDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;

import static hr.tvz.susac.java.web.flight.TestStaticUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests
{
    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    @WithMockUser(username = "userone1")
    public void findCurrentUser()
    {
        UserDTO userDTO = userService.findCurrentUser();

        assertNotNull(userDTO);
        assertEquals(USER_ONE_NAME, userDTO.getUsername());
    }

    @Test
    @Order(2)
    public void login()
    {
        LoginDTO loginDTO = createLoginDTO();

        UserDTO userDTO = userService.login(loginDTO);
        assertNotNull(userDTO);
        assertEquals(USER_ONE_NAME, userDTO.getUsername());
    }

    @Test
    @Order(3)
    public void register()
    {
        RegisterDTO registerDTO = createRegisterDTO();

        UserDTO userDTO = userService.register(registerDTO);

        assertNotNull(userDTO);
        assertEquals(USER_THREE_NAME, userDTO.getUsername());
    }
}
