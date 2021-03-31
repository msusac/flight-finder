package hr.tvz.susac.java.web.flight.dto.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.tvz.susac.java.web.flight.util.constraint.CurrencyCodeConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSaveSearchDTO {

    @JsonProperty(value = "originLocationCode")
    private String originLocationCode = "";

    @JsonProperty(value = "destinationLocationCode")
    private String destinationLocationCode = "";

    @JsonProperty(value = "departureDate")
    private LocalDate departureDate;

    @JsonProperty(value = "returnDate")
    private LocalDate returnDate;

    @Positive(message = "Passenger Count must be greater than zero!")
    @JsonProperty(value = "passengerCount")
    private Integer passengerCount = 1;

    @CurrencyCodeConstraint
    @JsonProperty(value = "currencyCode")
    private String currencyCode = "";
}
