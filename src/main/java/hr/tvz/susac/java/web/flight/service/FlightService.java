package hr.tvz.susac.java.web.flight.service;

import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSaveSearchDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSearchDTO;

import java.util.List;

public interface FlightService {

    List<FlightDTO> findAllSave();

    List<FlightDTO> search(String token, FlightSearchDTO searchDTO);

    List<FlightDTO> searchSave(FlightSaveSearchDTO searchDTO);

    FlightDTO save(FlightDTO flightDTO);

    void deleteSaveById(Long id);
}
