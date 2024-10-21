package omc.test.sensor.sensor.jobs;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omc.test.sensor.sensor.services.SensorManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SensorCleanupScheduler {

    private final SensorManager sensorManager;
    private final long DEFAULT_TIME = 24 * 3600000; // 24 hours ago;

    @Scheduled(cron = "0 0 * * * ?") // every 1 hour
    public void deleteInactiveSensors() {
        try {
            // Get the timestamp for 24 hours ago
            long thresholdTime = System.currentTimeMillis() - DEFAULT_TIME;

            // Delete sensors without sampling data in the past 24 hours
            sensorManager.deleteInactiveSensors(thresholdTime);
        } catch (Exception e) {
            log.error("Unable to delete inactive sensors", e);
        }
    }
}
