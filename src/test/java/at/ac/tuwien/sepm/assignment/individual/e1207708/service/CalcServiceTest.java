package at.ac.tuwien.sepm.assignment.individual.e1207708.service;

import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.exceptions.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.persistence.util.DBConnectionManager;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.exceptions.BadRequestException;
import at.ac.tuwien.sepm.assignment.individual.e1207708.service.impl.CalcService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class CalcServiceTest {

    @Autowired CalcService calcService;
    @Autowired ICalcService iCalcService;
    @Autowired
    private DBConnectionManager dbConnectionManager;

    /**
     * It is important to close the database connection after each test in order to clean the in-memory database
     */
    @After
    public void afterEachTest() throws PersistenceException {
        dbConnectionManager.closeConnection();
    }

    @Test
    public void givenValueNotRound() {
        Double value = 12.0123456789;
        Double result = calcService.getRoundedResult(value);

        assertEquals(true, result.equals(12.0123));
    }

    @Test
    public void givenValueToRound() {
        Double value = 12.123456789;
        Double result = calcService.getRoundedResult(value);

        assertEquals(true, result.equals(12.1235));
    }

    @Test
    public void calcPTest() {
        Double minSpeed = 42.5;
        Double maxSpeed = 57.1234;
        Double luckFactor = 1.05;
        Double reslt = calcService.calcP(minSpeed, maxSpeed, luckFactor);

        assertEquals(true, reslt.equals(57.1234));
    }

    @Test
    public void calcKTest() {
        Double skill = 1234.56789;
        Double result = calcService.calcK(skill);

        assertEquals(true, result.equals(1.0748));
    }

    @Test
    public void calcDTest() {
        Double minSpeed = 42.5;
        Double maxSpeed = 57.1234;
        Double skill = 1234.56789;
        Double luckFactor = 1.05;
        Double result = calcService.calcD(minSpeed, maxSpeed, skill, luckFactor);

        assertEquals(true, result.equals(64.4660));
    }

    @Test(expected=BadRequestException.class)
    public void givenMinSpeedNotInRangeTest() throws BadRequestException {
        Double maxSpeed = 60.0;
        Double skill = 2134.567;
        Double luckFactor = 1.00;

        Double minSpeedWrong = 39.9;

        iCalcService.calcAvgSpeed(minSpeedWrong, maxSpeed, skill, luckFactor);
    }

    @Test(expected=BadRequestException.class)
    public void givenMaxSpeedNotInRangeTest() throws BadRequestException {
        Double minSpeed = 40.0;
        Double skill = 2134.567;
        Double luckFactor = 1.00;

        Double maxSpeedWrong = 60.1;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeedWrong, skill, luckFactor);
    }

    @Test(expected=BadRequestException.class)
    public void givenMinAndMaxSwappedTest() throws BadRequestException {
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 2134.567;
        Double luckFactor = 1.00;

        iCalcService.calcAvgSpeed(maxSpeed, minSpeed, skill, luckFactor);
    }

    @Test(expected=BadRequestException.class)
    public void givenSkillIsPositiveInfinityTest() throws BadRequestException {
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double luckFactor = 1.00;

        Double skillWrong = Double.POSITIVE_INFINITY;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skillWrong, luckFactor);
    }

    @Test(expected=BadRequestException.class)
    public void givenSkillIsNegativeInfinityTest() throws BadRequestException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double luckFactor = 1.00;

        Double skillWrong = Double.NEGATIVE_INFINITY;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skillWrong, luckFactor);
    }

    @Test(expected=BadRequestException.class)
    public void givenSkillIsNaNTest() throws BadRequestException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double luckFactor = 1.00;

        Double skillWrong = Double.NaN;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skillWrong, luckFactor);
    }

    @Test(expected=BadRequestException.class)
    public void givenLuckFactorToSmallTest() throws BadRequestException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 2134.567;

        Double luckFactorWrong1 = 0.94999;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactorWrong1);
    }

    @Test(expected=BadRequestException.class)
    public void givenLuckFactorToBigTest() throws BadRequestException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 2134.567;

        Double luckFactorWrong2 = 1.051;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactorWrong2);
    }

    @Test
    public void iCalcAvgSpeedRadiansTest() throws BadRequestException {
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 999.999;
        Double luckFactor = 1.00;

        Double result = iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactor);
        assertEquals(true, result.equals(53.7400));
    }

    @Test
    public void iCalcAvgSpeedDegreeTest() throws BadRequestException {
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 999.999;
        Double luckFactor = 1.00;

        Double result = iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactor);
        assertNotEquals(true, result.equals(264.175));
    }

    @Test
    public void iCalcHorseSpeedTest() throws BadRequestException {
        Double minSpeed = 42.5;
        Double maxSpeed = 57.1234;
        Double luckFactor = 1.05;

        Double result = iCalcService.calcHorseSpeed(minSpeed, maxSpeed, luckFactor);
        assertEquals(true, result.equals(57.1234));
    }

    @Test
    public void iCalcSkillTest() throws BadRequestException {
        Double skill = 1234.56789;

        Double result = iCalcService.calcSkill(skill);
        assertEquals(true, result.equals(1.0748));
    }
}
