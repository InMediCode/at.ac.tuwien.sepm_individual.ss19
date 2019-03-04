package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.rest.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.exceptions.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.IHorseService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.util.mapper.HorseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

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

    @RequestMapping(value = "", method = RequestMethod.GET)
    public HorseDto[] getAll() {
        LOGGER.info("GET " + BASE_URL);
        try {
            return horseMapper.entityListToDtoArray(horseService.getAll());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during reading horses", e);
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

    @RequestMapping(value = "/name={name}", method = RequestMethod.POST)
    public HorseDto insertHorse(@PathVariable("name") String name) {
        HorseDto horseDto = new HorseDto();
        horseDto.setName(name);
        try {
            return horseMapper.entityToDto(horseService.insertHorse(horseMapper.dtoToEntity(horseDto)));
        } catch (ServiceException e) {
            //
        }

        return null;
    }

}
