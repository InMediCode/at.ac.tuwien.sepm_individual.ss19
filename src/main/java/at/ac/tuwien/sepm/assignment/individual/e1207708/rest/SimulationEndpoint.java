package at.ac.tuwien.sepm.assignment.individual.e1207708.rest;

import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.SimulationDto;
import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.SimulationResultDto;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.SimulationMapper;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.SimulationResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SimulationResultDto getOneById(@PathVariable("id") Integer id) {
        LOGGER.info("GET " + BASE_URL + "/" + id);
        try {
            return simulationResultMapper.entityToDto(simulationService.findOneById(id));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during read jockey with id " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading jockey: " + e.getMessage(), e);
        }
    }

    private SimulationResultDto[] getAll() {
        LOGGER.info("GET " + BASE_URL);
        try {
            return simulationResultMapper.entityListToDtoArray(simulationService.getAll());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during reading simulations", e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public SimulationResultDto[] getAllFilteredOrNot(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");

        if (name == null) {
            return this.getAll();
        } else {
            try {
                return simulationResultMapper.entityListToDtoArray(simulationService.getAllFilteredBy(name));
            } catch (ServiceException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during reading simulations with name " + name, e);
            }
        }
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
