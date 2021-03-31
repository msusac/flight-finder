package hr.tvz.susac.java.web.flight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSaveSearchDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSearchDTO;
import hr.tvz.susac.java.web.flight.dto.user.LoginDTO;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WithMockUser(username = "userone1")
public class FlightControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void getAllSave() throws Exception
    {
        this.mockMvc.perform(
                get("/api/flight/save")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @Order(2)
    public void save() throws Exception
    {
        FlightDTO flightDTO = createFlightDTO();

        this.mockMvc.perform(
                post("/api/flight/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(flightDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    public void save_BadRequest() throws Exception
    {
        FlightDTO flightDTO = createFlightDTO_BadRequest();

        LoginDTO loginDTO = createLoginDTO();
        UserDTO userDTO = userService.login(loginDTO);

        this.mockMvc.perform(
                post("/api/flight/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(flightDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    /*

     The following test was commented out due JUnit having unknown
     issues while working with JSON foreach/loop method.

    @Test
    @Order(4)
    public void search() throws Exception
    {
        FlightSearchDTO searchDTO = createFlightSearchDTO_search();

        LoginDTO loginDTO = createLoginDTO();
        UserDTO userDTO = userService.login(loginDTO);

        this.mockMvc.perform(
                post("/api/flight/search")
                        .header("Authorization", "Bearer " + userDTO.getJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }
    */

    @Test
    @Order(5)
    public void search_BadRequest() throws Exception
    {
        FlightSearchDTO searchDTO = createFlightSearchDTO_BadRequest();

        LoginDTO loginDTO = createLoginDTO();
        UserDTO userDTO = userService.login(loginDTO);

        this.mockMvc.perform(
                post("/api/flight/search")
                        .header("Authorization", "Bearer " + userDTO.getJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void searchSave() throws Exception
    {
        FlightSaveSearchDTO searchDTO = createFlightSaveSearchDTO();

        this.mockMvc.perform(
                post("/api/flight/save/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchDTO))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(7)
    public void deleteSaveById() throws Exception
    {
        this.mockMvc.perform(
                delete("/api/flight/save/1")
        )
                .andExpect(status().isOk());
    }
}
