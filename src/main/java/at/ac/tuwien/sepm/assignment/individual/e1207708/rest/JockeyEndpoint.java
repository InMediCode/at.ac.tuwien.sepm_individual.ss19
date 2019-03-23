package at.ac.tuwien.sepm.assignment.individual.e1207708.rest;

import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.JockeyDto;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.IJockeyService;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.JockeyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/jockeys")
public class JockeyEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(JockeyEndpoint.class);
    private static final String BASE_URL = "/api/v1/jockeys";
    private final IJockeyService jockeyService;
    private final JockeyMapper jockeyMapper;

    @Autowired
    public JockeyEndpoint(IJockeyService jockeyService, JockeyMapper jockeyMapper) {
        this.jockeyService = jockeyService;
        this.jockeyMapper = jockeyMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JockeyDto getOneById(@PathVariable("id") Integer id) {
        LOGGER.info("GET " + BASE_URL + "/" + id);
        try {
            return jockeyMapper.entityToDto(jockeyService.findOneById(id));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during read jockey with id " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading jockey: " + e.getMessage(), e);
        }
    }

    private JockeyDto[] getAll() {
        LOGGER.info("GET " + BASE_URL);
        try {
            return jockeyMapper.entityListToDtoArray(jockeyService.getAll());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during reading jockeys", e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public JockeyDto[] getAllFilteredOrNot(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");
        String skillString = requestParams.get("skill");
        Double skill = skillString == null ? null : Double.parseDouble(skillString);

        if (name == null || skill == null) {
            return this.getAll();
        } else {
            try {
                LOGGER.info("HERE");
                return jockeyMapper.entityListToDtoArray(jockeyService.getAllFilteredBy(name, skill));
            } catch (ServiceException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during read jockey with name " + name, e);
            }
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOneById(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE " + BASE_URL + "/" + id);
        try {
            jockeyService.deleteOneById(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during deleting jockey with id " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during deleting jockey: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<JockeyDto> insertJockey(@RequestBody JockeyDto jockeyDto) {
        if (jockeyDto != null) {
            LOGGER.info("Name: " + jockeyDto.getName());
            LOGGER.info("Skill: " + jockeyDto.getSkill());
        } else {
            LOGGER.info("NO DATA");
        }

        try {
            jockeyDto = jockeyMapper.entityToDto(jockeyService.insertJockey(jockeyMapper.dtoToEntity(jockeyDto)));
            return  ResponseEntity.status(HttpStatus.CREATED).body(jockeyDto);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new jockey: " + jockeyDto, e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody JockeyDto updateJockey(@PathVariable("id") Integer id, @RequestBody JockeyDto jockeyDto) {
        try {
            return jockeyMapper.entityToDto(jockeyService.updateJockey(id, jockeyMapper.dtoToEntity(jockeyDto)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new jockey: " + jockeyDto, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during updating jockey: " + e.getMessage(), e);
        }
    }
}
