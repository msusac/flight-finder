package hr.tvz.susac.java.web.flight.controller;

import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSaveSearchDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSearchDTO;
import hr.tvz.susac.java.web.flight.dto.user.UserDTO;
import hr.tvz.susac.java.web.flight.service.FlightService;
import hr.tvz.susac.java.web.flight.service.UserService;
import hr.tvz.susac.java.web.flight.util.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService flightService;
    private final UserService userService;

    @GetMapping("/save")
    public ResponseEntity<?> getAllSave()
    {
        log.info("Retrieving saved flight offers");
        return ResponseEntity.ok(flightService.findAllSave());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody FlightDTO flightDTO, Errors errors)
    {
        if (errors.hasErrors()) throw new BadRequestException(errors);

        flightDTO = flightService.save(flightDTO);
        log.info("Saved a Flight Offer: " + flightDTO);

        return ResponseEntity.ok("Saved Flight Offer!");
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestHeader("authorization") String jwt,
                                    @Valid @RequestBody FlightSearchDTO searchDTO,
                                    Errors errors)
    {
        if (errors.hasErrors()) throw new BadRequestException(errors);

        log.info("Retrieving flight offers from Air Api Search!");
        UserDTO userDTO = userService.findCurrentUserAirToken(jwt.split(" ")[1]);

        List<FlightDTO> flightDTOList = flightService.search(userDTO.getAirToken(), searchDTO);
        return ResponseEntity.ok(flightDTOList);
    }

    @PostMapping("/save/search")
    public ResponseEntity<?> searchSave(@Valid @RequestBody FlightSaveSearchDTO searchDTO, Errors errors)
    {
        if (errors.hasErrors()) throw new BadRequestException(errors);

        List<FlightDTO> flightDTOList = flightService.searchSave(searchDTO);
        return ResponseEntity.ok(flightDTOList);
    }

    @DeleteMapping("/save/{id}")
    public ResponseEntity<?> deleteSaveById(@PathVariable("id") Long id)
    {
        log.info("Deleting saved Flight Offer");
        flightService.deleteSaveById(id);
        return ResponseEntity.ok("Deleted saved Flight Offer!");
    }
}
