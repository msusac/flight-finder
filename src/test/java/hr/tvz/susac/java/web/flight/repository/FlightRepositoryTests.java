package hr.tvz.susac.java.web.flight.repository;

import hr.tvz.susac.java.web.flight.model.Flight;
import hr.tvz.susac.java.web.flight.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static hr.tvz.susac.java.web.flight.TestStaticUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlightRepositoryTests
{
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findAllByUserId()
    {
        List<Flight> flightList = flightRepository.findAllByUserId(USER_ONE_ID);
        assertEquals(3, flightList.size());
    }

    @Test
    @Order(2)
    public void findAllByParam()
    {
        List<Flight> flightList = flightRepository.findAllByParam(
                USER_ONE_ID, LOCATION_CODE_SYD, LOCATION_CODE_MAD,
                "2021-06-15", "2021-06-21", 1, ""
        );

        assertEquals(1, flightList.size());
    }

    @Test
    @Order(3)
    public void deleteByIdAndUserId()
    {
        flightRepository.deleteByIdAndUser_Id(FLIGHT_ONE_ID, USER_ONE_ID);
        List<Flight> flightList = flightRepository.findAllByUserId(USER_ONE_ID);

        assertEquals(2, flightList.size());
    }

    @Test
    @Order(4)
    public void save()
    {
        User user = userRepository.findOneByUsername(USER_ONE_NAME).orElse(null);

        Flight flight = createFlight(user);
        flight = flightRepository.save(flight);

        assertNotNull(flight);

        List<Flight> flightList = flightRepository.findAllByUserId(USER_ONE_ID);
        assertEquals(4, flightList.size());
    }
}
