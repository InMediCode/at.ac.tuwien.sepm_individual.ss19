package service;

import at.ac.tuwien.sepm.assignment.individual.service.ICalcService;
import at.ac.tuwien.sepm.assignment.individual.service.exceptions.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.impl.CalcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/*@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")*/
public class CalcServiceTest {
    private final CalcService calcService = new CalcService();
    private final ICalcService iCalcService = new CalcService();

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

    @Test(expected=ServiceException.class)
    public void givenMinSpeedNotInRangeTest() throws ServiceException{
        Double maxSpeed = 60.0;
        Double skill = 2134.567;
        Double luckFactor = 1.00;

        Double minSpeedWrong = 39.9;

        iCalcService.calcAvgSpeed(minSpeedWrong, maxSpeed, skill, luckFactor);
    }

    @Test(expected=ServiceException.class)
    public void givenMaxSpeedNotInRangeTest() throws ServiceException{
        Double minSpeed = 40.0;
        Double skill = 2134.567;
        Double luckFactor = 1.00;

        Double maxSpeedWrong = 60.1;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeedWrong, skill, luckFactor);
    }

    @Test(expected=ServiceException.class)
    public void givenMinAndMaxSwappedTest() throws ServiceException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 2134.567;
        Double luckFactor = 1.00;

        iCalcService.calcAvgSpeed(maxSpeed, minSpeed, skill, luckFactor);
    }

    @Test(expected=ServiceException.class)
    public void givenSkillIsPositiveInfinityTest() throws ServiceException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double luckFactor = 1.00;

        Double skillWrong = Double.POSITIVE_INFINITY;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skillWrong, luckFactor);
    }

    @Test(expected=ServiceException.class)
    public void givenSkillIsNegativeInfinityTest() throws ServiceException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double luckFactor = 1.00;

        Double skillWrong = Double.NEGATIVE_INFINITY;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skillWrong, luckFactor);
    }

    @Test(expected=ServiceException.class)
    public void givenSkillIsNaNTest() throws ServiceException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double luckFactor = 1.00;

        Double skillWrong = Double.NaN;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skillWrong, luckFactor);
    }

    @Test(expected=ServiceException.class)
    public void givenLuckFactorToSmallTest() throws ServiceException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 2134.567;

        Double luckFactorWrong1 = 0.94999;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactorWrong1);
    }

    @Test(expected=ServiceException.class)
    public void givenLuckFactorToBigTest() throws ServiceException{
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 2134.567;

        Double luckFactorWrong2 = 1.051;

        iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactorWrong2);
    }

    @Test
    public void iCalcAvgSpeedRadiansTest() throws ServiceException {
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 999.999;
        Double luckFactor = 1.00;

        Double result = iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactor);
        assertEquals(true, result.equals(53.7400));
    }

    @Test
    public void iCalcAvgSpeedDegreeTest() throws ServiceException {
        Double minSpeed = 40.0;
        Double maxSpeed = 60.0;
        Double skill = 999.999;
        Double luckFactor = 1.00;

        Double result = iCalcService.calcAvgSpeed(minSpeed, maxSpeed, skill, luckFactor);
        assertNotEquals(true, result.equals(264.175));
    }
}
