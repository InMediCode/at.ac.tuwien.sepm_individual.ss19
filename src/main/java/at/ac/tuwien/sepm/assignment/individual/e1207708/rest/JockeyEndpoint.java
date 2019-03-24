package at.ac.tuwien.sepm.assignment.individual.e1207708.rest;

import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.JockeyDto;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.entity.Jockey;
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
        LOGGER.info("GET ALL " + BASE_URL);
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

        Double skill = null;
        if (skillString != null) {
            try {
                skill = Double.parseDouble(skillString);
            } catch (NumberFormatException e) {
                LOGGER.error("Error during filtering" + " - " + e.getMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during filtering" + " - " + e.getMessage(), e);
            }
        }

        if (name == null && skill == null) {
            return this.getAll();
        } else {
            LOGGER.info("GET ALL filtered " + BASE_URL + " - by name: " + name + " skill: " + skill);
            Jockey filterJockey = new Jockey(null, name, skill, null, null, false);
            try {
                return jockeyMapper.entityListToDtoArray(jockeyService.getAllFilteredBy(filterJockey));
            } catch (ServiceException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during read jockey with name " + name, e);
            } catch (BadRequestException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during filtering jockeys with " + filterJockey + " - " + e.getMessage(), e);
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
        LOGGER.info("INSERT " + BASE_URL + " - " + jockeyDto);
        try {
            jockeyDto = jockeyMapper.entityToDto(jockeyService.insertJockey(jockeyMapper.dtoToEntity(jockeyDto)));
            return  ResponseEntity.status(HttpStatus.CREATED).body(jockeyDto);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new jockey: " + jockeyDto, e);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during inserting jockey: " + jockeyDto + " - " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody JockeyDto updateJockey(@PathVariable("id") Integer id, @RequestBody JockeyDto jockeyDto) {
        LOGGER.info("UPDATE " + BASE_URL + "/" + id + " - " + jockeyDto);
        try {
            return jockeyMapper.entityToDto(jockeyService.updateJockey(id, jockeyMapper.dtoToEntity(jockeyDto)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new jockey: " + jockeyDto, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during updating jockey: " + e.getMessage(), e);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during updating jockey: " + jockeyDto + " - " + e.getMessage(), e);
        }
    }
}
