package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.SimulationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/simulations")
public class SimulationEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseEndpoint.class);
    private static final String BASE_URL = "/api/v1/simulations";
    private final ISimulationService simulationService;
    private final SimulationMapper simulationMapper;

    @Autowired
    public SimulationEndpoint(ISimulationService simulationService, SimulationMapper simulationMapper) {
        this.simulationService = simulationService;
        this.simulationMapper = simulationMapper;
    }
}
