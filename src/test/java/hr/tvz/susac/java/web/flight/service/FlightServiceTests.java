package hr.tvz.susac.java.web.flight.service;

import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSaveSearchDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.List;

import static hr.tvz.susac.java.web.flight.TestStaticUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlightServiceTests {

    @Autowired
    private FlightService flightService;

    @Test
    @Order(1)
    @WithMockUser(username = "userone1")
    public void findAllSave()
    {
        List<FlightDTO> flightDTOList = flightService.findAllSave();
        assertEquals(3, flightDTOList.size());
    }

    /*
        The following test was commented out due JUnit having unknown
        issues while working with JSON foreach/loop method.

    @Test
    @Order(2)
    @WithMockUser(username = "userone1")
    public void search()
    {
        FlightSearchDTO flightSearchDTO = createFlightSearchDTO_search();
        LoginDTO loginDTO = createLoginDTO();

        UserDTO userDTO = userService.login(loginDTO);
        String airToken = jwtTokenProvider.extractAirApiTokenFromJwt(userDTO.getToken());

        List<FlightDTO> flightDTOList = flightService.search(airToken, flightSearchDTO);
        assertNotEquals(0, flightDTOList.size());
    }
    */

    @Test
    @Order(3)
    @WithMockUser(username = "userone1")
    public void searchSave()
    {
        FlightSaveSearchDTO searchDTO = createFlightSaveSearchDTO();

        List<FlightDTO> flightDTOList = flightService.searchSave(searchDTO);
        assertEquals(2, flightDTOList.size());
    }

    @Test
    @Order(4)
    @WithMockUser(username = "userone1")
    public void save()
    {
        FlightDTO flightDTO = createFlightDTO();
        flightDTO = flightService.save(flightDTO);

        assertNotNull(flightDTO);

        List<FlightDTO> flightDTOList = flightService.findAllSave();
        assertEquals(4, flightDTOList.size());
    }

    @Test
    @Order(5)
    @WithMockUser(username = "userone1")
    public void deleteSaveById()
    {
        flightService.deleteSaveById(FLIGHT_ONE_ID);

        List<FlightDTO> flightDTOList = flightService.findAllSave();
        assertEquals(2, flightDTOList.size());
    }
}
