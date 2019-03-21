package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationDto;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationResultDto;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.SimulationMapper;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.SimulationResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/simulations")
public class SimulationEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseEndpoint.class);
    private static final String BASE_URL = "/api/v1/simulations";
    private final ISimulationService simulationService;
    private final SimulationMapper simulationMapper;
    private final SimulationResultMapper simulationResultMapper;

    @Autowired
    public SimulationEndpoint(ISimulationService simulationService, SimulationMapper simulationMapper, SimulationResultMapper simulationResultMapper) {
        this.simulationService = simulationService;
        this.simulationMapper = simulationMapper;
        this.simulationResultMapper = simulationResultMapper;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity <SimulationResultDto> insertSimulation(@RequestBody SimulationDto simulationDto) {


        try {
            SimulationResultDto simulationResultDto = simulationResultMapper.entityToDto(simulationService.insertSimulation(simulationMapper.dtoToEntity(simulationDto)));
            return ResponseEntity.status(HttpStatus.CREATED).body(simulationResultDto);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new simulation: " + simulationDto, e);
        }
    }
}
