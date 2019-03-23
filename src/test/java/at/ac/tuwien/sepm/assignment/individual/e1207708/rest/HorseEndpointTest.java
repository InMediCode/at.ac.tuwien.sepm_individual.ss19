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
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class HorseEndpointTest {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String HORSE_URL = "/api/v1/horses";
    private static final HorseTestDto HORSE_1 = new HorseTestDto("", "Breed1", 45.0, 55.0);
    private static final HorseTestDto HORSE_2 = new HorseTestDto("Horse2", 40.0, 60.0);

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

    @Test
    public void whenSaveOneHorseWithEmptyStringName_thenStatus400() {
        HttpEntity<HorseTestDto> request = new HttpEntity<>(HORSE_1);
        ResponseEntity<HorseTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private void postHorse1() {
        REST_TEMPLATE.postForObject(BASE_URL + port + HORSE_URL, new HttpEntity<>(HORSE_1), HorseTestDto.class);
    }

    private void postHorse2() {
        REST_TEMPLATE.postForObject(BASE_URL + port + HORSE_URL, new HttpEntity<>(HORSE_2), HorseTestDto.class);
    }
}
