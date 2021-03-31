package hr.tvz.susac.java.web.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.susac.java.web.flight.dto.user.LoginDTO;
import hr.tvz.susac.java.web.flight.dto.user.RegisterDTO;
import hr.tvz.susac.java.web.flight.dto.user.UserDTO;
import hr.tvz.susac.java.web.flight.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static hr.tvz.susac.java.web.flight.TestStaticUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    @WithMockUser(username = "userone1")
    public void getCurrentUser() throws Exception
    {
        this.mockMvc.perform(
                get("/api/user")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "")
    public void getCurrentUser_Unauthorized() throws Exception
    {
        this.mockMvc.perform(
                get("/api/user")
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    public void login() throws Exception{
        LoginDTO loginDTO = createLoginDTO();

        this.mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(4)
    public void login_Forbidden() throws Exception{
        LoginDTO loginDTO = createLoginDTO_Forbidden();

        this.mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(5)
    public void login_BadRequest_FailedValidation() throws Exception
    {
        LoginDTO loginDTO = createLoginDTO_BadRequest();

        this.mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void register() throws Exception
    {
        RegisterDTO registerDTO = createRegisterDTO();

        this.mockMvc.perform(
                post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    public void register_BadRequest() throws Exception
    {
        RegisterDTO registerDTO = createRegisterDTO_BadRequest();

        this.mockMvc.perform(
                post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    public void register_BadRequest_UsernameEmailTaken() throws Exception
    {
        RegisterDTO registerDTO = createRegisterDTO_BadRequest_UsernameEmailTaken();

        this.mockMvc.perform(
                post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    @WithMockUser(username = "userone1")
    public void getAirApiToken() throws Exception
    {
        LoginDTO loginDTO = createLoginDTO();
        UserDTO userDTO = userService.login(loginDTO);

        this.mockMvc.perform(
                get("/api/airtoken")
                        .header("Authorization", "Bearer " + userDTO.getJwtToken())
        )
                .andExpect(status().isOk());
    }
}
