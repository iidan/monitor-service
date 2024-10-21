package omc.test.sensor.sensor.jobs;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omc.test.sensor.sensor.entites.Sensor;
import omc.test.sensor.sensor.entites.SensorHistory;
import omc.test.sensor.sensor.services.SensorHistoryManager;
import omc.test.sensor.sensor.services.SensorManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class SensorUpdateScheduler {

    private final SensorManager sensorManager;
    private final SensorHistoryManager sensorHistoryManager;

    @Scheduled(cron = "0 0 * * * ?") // every 1 hour
    public void executeTaskEveryHour() {

        try {
            List<Sensor> sensorMap = sensorManager.getAvailableSensors();
            for (Sensor sensor : sensorMap) {
                sensorHistoryManager.saveSensorHistory(new SensorHistory(sensor));
            }

        } catch (Exception e) {
            log.error("Unable to create new records", e);
        }

    }

}
