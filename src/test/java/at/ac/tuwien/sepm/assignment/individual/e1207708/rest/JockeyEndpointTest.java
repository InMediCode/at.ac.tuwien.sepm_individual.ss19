package at.ac.tuwien.sepm.assignment.individual.e1207708.rest;

import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.util.DBConnectionManager;
import at.ac.tuwien.sepm.assignment.individual.integration.dto.JockeyTestDto;
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
public class JockeyEndpointTest {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String JOCKEY_URL = "/api/v1/jockeys";
    private static final String JOCKEY_URL_UPDATE = "/api/v1/jockeys/1";
    private static final JockeyTestDto JOCKEY_VALID = new JockeyTestDto("Jockey1", -2000.0);
    private static final JockeyTestDto JOCKEY_NAME_EMPTY_STRING = new JockeyTestDto("", -2000.0);
    private static final JockeyTestDto JOCKEY_NAME_NULL = new JockeyTestDto(null, -2000.0);
    private static final JockeyTestDto JOCKEY_SKILL_NULL = new JockeyTestDto("Jockey2", null);
    private static final JockeyTestDto JOCKEY_SKILL_NAN = new JockeyTestDto("Jockey2", Double.NaN);
    private static final JockeyTestDto JOCKEY_SKILL_NEGATIVE_INFINITY = new JockeyTestDto("Jockey2", Double.NEGATIVE_INFINITY);
    private static final JockeyTestDto JOCKEY_SKILL_POSITIVE_INFINITY = new JockeyTestDto("Jockey2", Double.POSITIVE_INFINITY);
    private static final JockeyTestDto JOCKEY_VALID_UPDATE = new JockeyTestDto("newJockeyName", 100.0);

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
    public void whenSaveOneJockeyWithEmptyStringName_thenStatus400() throws HttpClientErrorException {
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_NAME_EMPTY_STRING);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL, HttpMethod.POST, request, JockeyTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneJockeyWithNameNull_thenStatus400() throws HttpClientErrorException {
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_NAME_NULL);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL, HttpMethod.POST, request, JockeyTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneJockeyWithSkillNull_thenStatus400() throws HttpClientErrorException {
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_SKILL_NULL);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL, HttpMethod.POST, request, JockeyTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneJockeyWithSkillNaN_thenStatus400() throws HttpClientErrorException {
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_SKILL_NAN);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL, HttpMethod.POST, request, JockeyTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneJockeyWithSkillNegativeInfinity_thenStatus400() throws HttpClientErrorException {
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_SKILL_NEGATIVE_INFINITY);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL, HttpMethod.POST, request, JockeyTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneJockeyWithSkillPositiveInfinity_thenStatus400() throws HttpClientErrorException {
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_SKILL_POSITIVE_INFINITY);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL, HttpMethod.POST, request, JockeyTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    //Update Tests
    @Test(expected= HttpClientErrorException.class)
    public void whenUpdateJockeyWithEmptyStringName_thenStatus400() throws HttpClientErrorException {
        postValidJockey();
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_NAME_EMPTY_STRING);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL_UPDATE, HttpMethod.PUT, request, JockeyTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void whenUpdateJockey_thenStatus200() {
        postValidJockey();
        HttpEntity<JockeyTestDto> request = new HttpEntity<>(JOCKEY_VALID_UPDATE);
        ResponseEntity<JockeyTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + JOCKEY_URL_UPDATE, HttpMethod.PUT, request, JockeyTestDto.class);
        JockeyTestDto jockeyResponse = response.getBody();
        assertNotNull(jockeyResponse);
        assertEquals(jockeyResponse.getName(), JOCKEY_VALID_UPDATE.getName());
        assertEquals(jockeyResponse.getSkill(), JOCKEY_VALID_UPDATE.getSkill());
    }

    private void postValidJockey() {
        REST_TEMPLATE.postForObject(BASE_URL + port + JOCKEY_URL, new HttpEntity<>(JOCKEY_VALID), JockeyTestDto.class);
    }
}
