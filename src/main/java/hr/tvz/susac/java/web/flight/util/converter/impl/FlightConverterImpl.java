package hr.tvz.susac.java.web.flight.util.converter.impl;

import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.model.Flight;
import hr.tvz.susac.java.web.flight.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Qualifier("FlightConverter")
public class FlightConverterImpl implements ConverterUtil<Flight, FlightDTO>
{
    private final ModelMapper modelMapper;

    @Override
    public FlightDTO convertToDTO(Flight flight)
    {
        return modelMapper.map(flight, FlightDTO.class);
    }

    @Override
    public Flight convertToEntity(FlightDTO flightDTO)
    {
        return modelMapper.map(flightDTO, Flight.class);
    }
}

