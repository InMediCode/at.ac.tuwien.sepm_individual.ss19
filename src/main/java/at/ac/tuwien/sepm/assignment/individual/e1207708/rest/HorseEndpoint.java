package at.ac.tuwien.sepm.assignment.individual.e1207708.rest;

import at.ac.tuwien.sepm.assignment.individual.e1207708.rest.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.IHorseService;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.HorseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/horses")
public class HorseEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseEndpoint.class);
    private static final String BASE_URL = "/api/v1/horses";
    private final IHorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(IHorseService horseService, HorseMapper horseMapper) {
        this.horseService = horseService;
        this.horseMapper = horseMapper;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HorseDto getOneById(@PathVariable("id") Integer id) {
        LOGGER.info("GET " + BASE_URL + "/" + id);
        try {
            return horseMapper.entityToDto(horseService.findOneById(id));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during read horse with id " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse: " + e.getMessage(), e);
        }
    }

    private HorseDto[] getAll() {
        LOGGER.info("GET " + BASE_URL);
        try {
            return horseMapper.entityListToDtoArray(horseService.getAll());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during reading horses", e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public HorseDto[] getAllFilteredOrNot(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");
        String breed = requestParams.get("breed");
        String minString = requestParams.get("minSpeed");
        String maxString = requestParams.get("maxSpeed");
        Double minSpeed = minString == null ? null : Double.parseDouble(minString);
        Double maxSpeed = maxString == null ? null : Double.parseDouble(maxString);

        if (name == null || breed == null || minSpeed == null || maxSpeed == null ) {
            return this.getAll();
        } else {
            try {
                LOGGER.info("HERE");
                return horseMapper.entityListToDtoArray(horseService.getAllFilteredBy(name, breed, minSpeed, maxSpeed));
            } catch (ServiceException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during read horse with name " + name, e);
            }
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOneById(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE " + BASE_URL + "/" + id);
        try {
            horseService.deleteOneById(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during deleting horse with id " + id, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during deleting horse: " + e.getMessage(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<HorseDto> insertHorse(@RequestBody HorseDto horseDto) {
        if (horseDto != null) {
            LOGGER.info("Name: " + horseDto.getName());
            LOGGER.info("Breed: " + horseDto.getBreed());
            LOGGER.info("minSpeed: " + horseDto.getMinSpeed());
            LOGGER.info("maxSpeed: " + horseDto.getMaxSpeed());
        } else {
            LOGGER.info("NO DATA");
        }

        try {
            horseDto = horseMapper.entityToDto(horseService.insertHorse(horseMapper.dtoToEntity(horseDto)));
            return  ResponseEntity.status(HttpStatus.CREATED).body(horseDto);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new horse: " + horseDto, e);
        }

        //rotating log files
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody HorseDto updateHorse(@PathVariable("id") Integer id, @RequestBody HorseDto horseDto) {
        try {
            return horseMapper.entityToDto(horseService.updateHorse(id, horseMapper.dtoToEntity(horseDto)));
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during inserting new horse: " + horseDto, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during updating horse: " + e.getMessage(), e);
        }
    }
}
