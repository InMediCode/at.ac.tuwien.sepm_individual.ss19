package at.ac.tuwien.sepm.assignment.individual.util.localdatetime;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EpochMilliDateTimer {
    public LocalDateTime getLocalDateTime() {
        //replace nano because saved in DB only with EpochMilli
        LocalDateTime localDateTime = LocalDateTime.now();
        int nano = (localDateTime.getNano() / 1000000) * 1000000;
        localDateTime = localDateTime.withNano(nano);

        return localDateTime;
    }
}
