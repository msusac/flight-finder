package hr.tvz.susac.java.web.flight.dto.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.tvz.susac.java.web.flight.util.constraint.CurrencyCodeConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchDTO {

    @NotNull(message = "Origin Location Code is required!")
    @Pattern(regexp = "^[A-Z]+$",
            message = "Origin Location Code accepts only alphabetic uppercase letters without any white space!")
    @Size(min = 2, max = 4, message = "Origin Location Code contains only 2-4 characters!")
    @JsonProperty(value = "originLocationCode")
    private String originLocationCode;

    @NotNull(message = "Destination Location Code is required!")
    @Pattern(regexp = "^[A-Z]+$",
            message = "Destination Location Code accepts only alphabetic uppercase letters without any white space!")
    @Size(min = 2, max = 4, message = "Destination Location Code contains only 2-4 characters!")
    @JsonProperty(value = "destinationLocationCode")
    private String destinationLocationCode;

    @Future(message = "Future Departure Date is required!")
    @NotNull(message = "Departure Date is required!")
    @JsonProperty(value = "departureDate")
    private LocalDate departureDate;

    @Future(message = "Future Return Date is required!")
    @JsonProperty(value = "returnDate")
    private LocalDate returnDate;

    @Range(min = 1, max = 9, message = "Passenger Count must be between 1 and 9!")
    @NotNull(message = "Passenger Count is required!")
    @JsonProperty(value = "passengerCount")
    private Integer passengerCount;

    @CurrencyCodeConstraint
    @JsonProperty(value = "currencyCode")
    private String currencyCode = "";

    @AssertTrue(message = "Return Date must be greater than or equal to Departure Date!")
    private boolean isValidReturnDate()
    {
        if (Objects.isNull(returnDate)) return true;

        if (departureDate.isBefore(returnDate)
                || departureDate.equals(returnDate)) return true;

        return false;
    }
}
