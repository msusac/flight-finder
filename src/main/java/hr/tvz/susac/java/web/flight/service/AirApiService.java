package hr.tvz.susac.java.web.flight.service;

import hr.tvz.susac.java.web.flight.dto.flight.FlightDTO;
import hr.tvz.susac.java.web.flight.dto.flight.FlightSearchDTO;
import hr.tvz.susac.java.web.flight.util.exception.BadRequestException;
import hr.tvz.susac.java.web.flight.util.exception.InternalServerErrorException;
import hr.tvz.susac.java.web.flight.util.exception.UnauthorizedRequestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AirApiService
{
    @Value("${amadeus.air-api.client.id}")
    private String clientId;

    @Value("${amadeus.air-api.client.secret}")
    private String clientSecret;

    @Value("${amadeus.air-api.client.grant_type}")
    private String grantType;

    private final ModelMapper modelMapper;
    private final WebClient.Builder webClientBuilder;

    public AirApiService(ModelMapper modelMapper, WebClient.Builder webClientBuilder) {
        this.modelMapper = modelMapper;
        this.webClientBuilder = webClientBuilder;
    }

    public List<FlightDTO> requestFlightOffersSearch(String token, FlightSearchDTO searchDTO)
    {
        String response = webClientBuilder.exchangeStrategies(ExchangeStrategies.builder()
                    .codecs(configurer -> configurer.defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024)).build())
                .build()
                .get()
                .uri(buildUriFlightOffersSearch(searchDTO))
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(
                                body -> new BadRequestException(body)
                        )
                )
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(
                                body -> new InternalServerErrorException(body)
                        )
                )
                .bodyToMono(String.class).block();

        return getFlightListFromJsonResponse(response, searchDTO);
    }

    public URI buildUriFlightOffersSearch(FlightSearchDTO searchDTO)
    {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("https://test.api.amadeus.com/v2/shopping/flight-offers");
        uriBuilder.queryParam("originLocationCode", searchDTO.getOriginLocationCode());
        uriBuilder.queryParam("destinationLocationCode", searchDTO.getDestinationLocationCode());
        uriBuilder.queryParam("departureDate", searchDTO.getDepartureDate());
        uriBuilder.queryParam("adults", searchDTO.getPassengerCount());

        if(!Objects.isNull(searchDTO.getReturnDate()))
            uriBuilder.queryParam("returnDate", searchDTO.getReturnDate());

        if(!searchDTO.getCurrencyCode().equals(""))
            uriBuilder.queryParam("currencyCode", searchDTO.getCurrencyCode());

        return uriBuilder.build().toUri();
    }

    public String requestToken()
    {
        BodyInserters.FormInserter<String> bodyInsert = BodyInserters
                .fromFormData("client_id", clientId)
                .with("client_secret", clientSecret)
                .with("grant_type", grantType);

        String response = webClientBuilder.build()
                .post()
                .uri("https://test.api.amadeus.com/v1/security/oauth2/token")
                .body(bodyInsert)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(
                                body -> new UnauthorizedRequestException(body)
                        )
                )
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(
                                body -> new InternalServerErrorException(body)
                        )
                )
                .bodyToMono(String.class).block();

        log.info("Generating Air Api Auth Token!");

        return getTokenFromJsonResponse(response);
    }

    private String getTokenFromJsonResponse(String response)
    {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("access_token");
    }

    private List<FlightDTO> getFlightListFromJsonResponse(String json, FlightSearchDTO searchDTO)
    {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.optJSONArray("data");

        List<FlightDTO> flightDTOList = new ArrayList<>();

        for(Object object: jsonArray)
        {
            JSONObject item = (JSONObject) object;

            FlightDTO flightDTO = modelMapper.map(searchDTO, FlightDTO.class);
            flightDTO.setId(getIdFromJson(item));
            flightDTO.setTransferCount(getTransferCountFromJson(item));
            flightDTO.setCurrencyCode(getCurrencyCodeFromJson(item));
            flightDTO.setTotalPrice(getTotalPriceFromJson(item));

            flightDTOList.add(flightDTO);
        }

        return sortListByTotalPrice(flightDTOList);
    }

    private String getCurrencyCodeFromJson(JSONObject object)
    {
        return object.getJSONObject("price").getString("currency");
    }

    private Long getIdFromJson(JSONObject object)
    {
        return object.getLong("id");
    }

    private Integer getTransferCountFromJson(JSONObject object)
    {
        JSONArray jsonArray = object.optJSONArray("itineraries");
        AtomicInteger count = new AtomicInteger();

        for(Object itinerary: jsonArray){
            JSONObject item= (JSONObject) itinerary;
            count.addAndGet(item.getJSONArray("segments").length());
        }

        return count.get();
    }

    private Double getTotalPriceFromJson(JSONObject object)
    {
        return object.getJSONObject("price").getDouble("grandTotal");
    }

    private List<FlightDTO> sortListByTotalPrice(List<FlightDTO> flightDTOList)
    {
        return flightDTOList.stream()
                .sorted(Comparator.comparing(FlightDTO::getTotalPrice))
                .collect(Collectors.toList());
    }
}
