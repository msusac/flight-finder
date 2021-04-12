package hr.tvz.susac.java.web.flight.service.impl;

import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSaveSearchDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSearchDTO;
import hr.tvz.susac.java.web.flight.model.Flight;
import hr.tvz.susac.java.web.flight.repository.FlightRepository;
import hr.tvz.susac.java.web.flight.service.AirApiService;
import hr.tvz.susac.java.web.flight.service.FlightService;
import hr.tvz.susac.java.web.flight.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class FlightServiceImpl implements FlightService
{
    private final AirApiService airApiService;

    private final FlightRepository flightRepository;
    private final UserServiceImpl userServiceImpl;

    private final ConverterUtil<Flight, FlightDTO> converterUtil;

    @Override
    public List<FlightDTO> findAllSave()
    {
        List<Flight> flightList = flightRepository.findAllByUserId(userServiceImpl.getCurrentUser().getId());

        return flightList.stream().map(converterUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightDTO> search(String jwt, FlightSearchDTO searchDTO)
    {
        return airApiService.requestFlightOffersSearch(jwt, searchDTO);
    }

    @Override
    public List<FlightDTO> searchSave(FlightSaveSearchDTO searchDTO)
    {
        String origin = searchDTO.getOriginLocationCode();
        String destination = searchDTO.getDestinationLocationCode();
        String departureDate = "";
        String returnDate = "";
        Integer passengerCount = searchDTO.getPassengerCount();
        String currencyCode = searchDTO.getCurrencyCode();

        if(!Objects.isNull(searchDTO.getDepartureDate()))
            departureDate = searchDTO.getDepartureDate().toString();

        if(!Objects.isNull(searchDTO.getReturnDate()))
            returnDate = searchDTO.getReturnDate().toString();

        List<Flight> flightList = flightRepository.findAllByParam(
                userServiceImpl.getCurrentUser().getId(),
                origin, destination, departureDate,
                returnDate, passengerCount, currencyCode
        );

        return flightList.stream().map(converterUtil::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FlightDTO save(FlightDTO flightDTO)
    {
        Flight flight = converterUtil.convertToEntity(flightDTO);
        flight.setId(null);
        flight.setUser(userServiceImpl.getCurrentUser());

        flight = flightRepository.save(flight);

        return converterUtil.convertToDTO(flight);
    }

    @Override
    public void deleteSaveById(Long id)
    {
        flightRepository.deleteByIdAndUser_Id(id, userServiceImpl.getCurrentUser().getId());
    }
}
