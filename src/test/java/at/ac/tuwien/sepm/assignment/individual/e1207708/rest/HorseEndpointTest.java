package at.ac.tuwien.sepm.assignment.individual.e1207708.rest;

import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.util.DBConnectionManager;
import at.ac.tuwien.sepm.assignment.individual.integration.dto.HorseTestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class HorseEndpointTest {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String HORSE_URL = "/api/v1/horses";
    private static final String HORSE_URL_UPDATE = "/api/v1/horses/1";
    private static final HorseTestDto HORSE_VALID = new HorseTestDto("horsename", "Breed1", 45.0, 55.0);
    private static final HorseTestDto HORSE_NAME_EMPTY_STRING = new HorseTestDto("", "Breed1", 45.0, 55.0);
    private static final HorseTestDto HORSE_NAME_NULL = new HorseTestDto(null, 40.0, 60.0);
    private static final HorseTestDto HORSE_MINSPEED_TO_SMALL = new HorseTestDto("horsename", 39.9, 60.0);
    private static final HorseTestDto HORSE_MAXSPEED_TO_BIG = new HorseTestDto("horsename", 39.9, 60.1);
    private static final HorseTestDto HORSE_MINSPEED_BIGGER_THAN_MAXSPEED = new HorseTestDto("horsename", 50.0, 49.9);
    private static final HorseTestDto HORSE_MINSPEED_NULL = new HorseTestDto("horsename", null, 60.0);
    private static final HorseTestDto HORSE_MAXSPEED_NULL = new HorseTestDto("horsename", 40.0, null);
    private static final HorseTestDto HORSE_VALID_UPDATE = new HorseTestDto("newName", "newBreed", 40.0, 60.0);

    @LocalServerPort
    private int port;
    @Autowired
    private DBConnectionManager dbConnectionManager;

    /**
     * It is important to close the database connection after each test in order to clean the in-memory database
     */
    @After
    public void afterEachTest() throws PersistenceException {
        dbConnectionManager.closeConnection();
    }

    //Save Tests
    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneHorseWithEmptyStringName_thenStatus400() throws HttpClientErrorException {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_NAME_EMPTY_STRING);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneHorseWithNameNull_thenStatus400() throws HttpClientErrorException {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_NAME_NULL);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneHorseWithMinSpeedToSmall_thenStatus400() throws HttpClientErrorException {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_MINSPEED_TO_SMALL);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneHorseWithMaxSpeedToBig_thenStatus400() throws HttpClientErrorException {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_MAXSPEED_TO_BIG);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneHorseWithMinSpeedBiggerThanMaxSpeed_thenStatus400() throws HttpClientErrorException {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_MINSPEED_BIGGER_THAN_MAXSPEED);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneHorseWithMinSpeedNull_thenStatus400() throws HttpClientErrorException {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_MINSPEED_NULL);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneHorseWithMaxSpeedNull_thenStatus400() throws HttpClientErrorException {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_MAXSPEED_NULL);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    //Update Tests
    @Test(expected= HttpClientErrorException.class)
    public void whenUpdateHorseWithEmptyStringName_thenStatus400() throws HttpClientErrorException {
        postValidHorse();
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_NAME_EMPTY_STRING);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL_UPDATE, HttpMethod.PUT, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void whenUpdateHorse_thenStatus200() {
        postValidHorse();
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_VALID_UPDATE);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL_UPDATE, HttpMethod.PUT, request, HorseTestDto.class);
        HorseTestDto horseResponse = response.getBody();
        assertNotNull(horseResponse);
        assertEquals(horseResponse.getName(), HORSE_VALID_UPDATE.getName());
        assertEquals(horseResponse.getBreed(), HORSE_VALID_UPDATE.getBreed());
        assertEquals(horseResponse.getMinSpeed(), HORSE_VALID_UPDATE.getMinSpeed());
        assertEquals(horseResponse.getMaxSpeed(), HORSE_VALID_UPDATE.getMaxSpeed());
    }

    private void postValidHorse() {
        REST_TEMPLATE.postForObject(BASE_URL + port + HORSE_URL, new HttpEntity<>(HORSE_VALID), HorseTestDto.class);
    }
}
