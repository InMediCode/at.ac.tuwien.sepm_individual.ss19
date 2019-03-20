package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.rest.dto.SimulationDto;
import at.ac.tuwien.sepm.assignment.individual.service.ISimulationService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.SimulationMapper;
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

    @Autowired
    public SimulationEndpoint(ISimulationService simulationService, SimulationMapper simulationMapper) {
        this.simulationService = simulationService;
        this.simulationMapper = simulationMapper;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity <SimulationDto> insertSimulation(@RequestBody SimulationDto simulationDto) {
        /*if (simulationDto != null) {
            LOGGER.info("Name: " + simulationDto.getName());
            ArrayList<ParticipantDto> participants = simulationDto.getParticipants();

            if (participants == null) {
                LOGGER.info("No participants found");
            } else {
                LOGGER.info("Participants found: " + participants.size());
            }

            for (ParticipantDto participant : participants) {
                LOGGER.info("Name: " + participant.toString());
            }
        } else {
            LOGGER.info("NO DATA");
        }*/

        try {
            simulationDto = simulationMapper.entityToDto(simulationService.insertSimulation(simulationMapper.dtoToEntity(simulationDto)));
            return ResponseEntity.status(HttpStatus.CREATED).body(simulationDto);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new simulation: " + simulationDto, e);
        }
    }
}
