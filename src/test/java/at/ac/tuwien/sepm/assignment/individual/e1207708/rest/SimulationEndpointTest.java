package at.ac.tuwien.sepm.assignment.individual.e1207708.rest;

import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.util.DBConnectionManager;
import at.ac.tuwien.sepm.assignment.individual.integration.dto.SimulationInputTestDto;
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

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class SimulationEndpointTest {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String BASE_URL = "http://localhost:";
    private static final String SIMULATION_URL = "/api/v1/simulations";
    private static final SimulationInputTestDto SIMULATION_NAME_EMPTY_STRING = new SimulationInputTestDto("", new ArrayList<>());
    private static final SimulationInputTestDto SIMULATION_NAME_NULL = new SimulationInputTestDto(null, new ArrayList<>());
    private static final SimulationInputTestDto SIMULATION_PARTICIPANTS_NULL = new SimulationInputTestDto("Simulation 1", null);

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
    public void whenSaveOneSimulationWithEmptyStringName_thenStatus400() throws HttpClientErrorException {
        HttpEntity<SimulationInputTestDto> request = new HttpEntity<>(SIMULATION_NAME_EMPTY_STRING);
        ResponseEntity<SimulationInputTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + SIMULATION_URL, HttpMethod.POST, request, SimulationInputTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneSimulationWithgNameNull_thenStatus400() throws HttpClientErrorException {
        HttpEntity<SimulationInputTestDto> request = new HttpEntity<>(SIMULATION_NAME_NULL);
        ResponseEntity<SimulationInputTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + SIMULATION_URL, HttpMethod.POST, request, SimulationInputTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected= HttpClientErrorException.class)
    public void whenSaveOneSimulationWithSimulationParticipantsNull_thenStatus400() throws HttpClientErrorException {
        HttpEntity<SimulationInputTestDto> request = new HttpEntity<>(SIMULATION_PARTICIPANTS_NULL);
        ResponseEntity<SimulationInputTestDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + SIMULATION_URL, HttpMethod.POST, request, SimulationInputTestDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
