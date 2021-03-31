package hr.tvz.susac.java.web.flight;

import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSaveSearchDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSearchDTO;
import hr.tvz.susac.java.web.flight.dto.user.LoginDTO;
import hr.tvz.susac.java.web.flight.dto.user.RegisterDTO;
import hr.tvz.susac.java.web.flight.model.Flight;
import hr.tvz.susac.java.web.flight.model.User;

import java.time.LocalDate;

public class TestStaticUtil
{
    public static Long FLIGHT_ONE_ID = (long) 1;

    public static Long USER_ONE_ID = (long) 1;
    public static String USER_ONE_NAME = "userone1";
    public static String USER_ONE_PASSWORD = "userone1";
    public static String USER_ONE_EMAIL = "userone1@test.com";

    public static String USER_THREE_NAME = "userthree3";
    public static String USER_THREE_PASSWORD = "userthree3";
    public static String USER_THREE_EMAIL = "userthree3@test.com";

    public static String LOCATION_CODE_BKK = "BKK";
    public static String LOCATION_CODE_MAD = "MAD";
    public static String LOCATION_CODE_SYD = "SYD";

    public static String CURRENCY_CODE_EUR = "EUR";
    public static String CURRENCY_CODE_HRK = "HRK";

    public static User createNewUser()
    {
        User user = new User();
        user.setUsername(USER_THREE_NAME);
        user.setEmail(USER_THREE_EMAIL);
        user.setPassword(USER_THREE_PASSWORD);

        return user;
    }

    public static LoginDTO createLoginDTO()
    {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(USER_ONE_NAME);
        loginDTO.setPassword(USER_ONE_PASSWORD);

        return loginDTO;
    }

    public static LoginDTO createLoginDTO_BadRequest()
    {
        return new LoginDTO();
    }

    public static LoginDTO createLoginDTO_Forbidden()
    {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(USER_THREE_NAME);
        loginDTO.setPassword(USER_THREE_PASSWORD);

        return loginDTO;
    }

    public static RegisterDTO createRegisterDTO()
    {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(USER_THREE_NAME);
        registerDTO.setEmail(USER_THREE_EMAIL);
        registerDTO.setPassword(USER_THREE_PASSWORD);

        return registerDTO;
    }

    public static RegisterDTO createRegisterDTO_BadRequest()
    {
        return new RegisterDTO();
    }

    public static RegisterDTO createRegisterDTO_BadRequest_UsernameEmailTaken()
    {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(USER_ONE_NAME);
        registerDTO.setEmail(USER_ONE_EMAIL);
        registerDTO.setPassword(USER_THREE_PASSWORD);

        return registerDTO;
    }

    public static Flight createFlight(User user)
    {
        Flight flight = new Flight();
        flight.setOriginLocationCode(LOCATION_CODE_BKK);
        flight.setDestinationLocationCode(LOCATION_CODE_MAD);
        flight.setDepartureDate(LocalDate.parse("2021-06-14"));
        flight.setReturnDate(LocalDate.parse("2021-06-24"));
        flight.setPassengerCount(4);
        flight.setTransferCount(2);
        flight.setCurrencyCode(CURRENCY_CODE_HRK);
        flight.setTotalPrice(Double.parseDouble("1020.20"));
        flight.setUser(user);

        return flight;
    }

    public static FlightDTO createFlightDTO()
    {
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOriginLocationCode(LOCATION_CODE_BKK);
        flightDTO.setDestinationLocationCode(LOCATION_CODE_MAD);
        flightDTO.setDepartureDate(LocalDate.parse("2021-06-14"));
        flightDTO.setReturnDate(LocalDate.parse("2021-06-24"));
        flightDTO.setPassengerCount(4);
        flightDTO.setTransferCount(2);
        flightDTO.setCurrencyCode(CURRENCY_CODE_HRK);
        flightDTO.setTotalPrice(Double.parseDouble("1020.20"));

        return flightDTO;
    }

    public static FlightDTO createFlightDTO_BadRequest()
    {
        FlightDTO flightDTO = new FlightDTO();

        return flightDTO;
    }

    public static FlightSearchDTO createFlightSearchDTO_search()
    {
        FlightSearchDTO flightSearchDTO = new FlightSearchDTO();
        flightSearchDTO.setOriginLocationCode(LOCATION_CODE_BKK);
        flightSearchDTO.setDestinationLocationCode(LOCATION_CODE_MAD);
        flightSearchDTO.setDepartureDate(LocalDate.parse("2021-06-14"));
        flightSearchDTO.setReturnDate(LocalDate.parse("2021-06-24"));
        flightSearchDTO.setCurrencyCode(CURRENCY_CODE_EUR);
        flightSearchDTO.setPassengerCount(3);

        return flightSearchDTO;
    }

    public static FlightSearchDTO createFlightSearchDTO_BadRequest()
    {
        FlightSearchDTO flightSearchDTO = new FlightSearchDTO();

        return flightSearchDTO;
    }

    public static FlightSaveSearchDTO createFlightSaveSearchDTO()
    {
        FlightSaveSearchDTO flightSaveSearchDTO = new FlightSaveSearchDTO();
        flightSaveSearchDTO.setOriginLocationCode(LOCATION_CODE_SYD);

        return flightSaveSearchDTO;
    }
}
