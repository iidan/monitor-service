package omc.test.sensor.sensor.controllers;

import omc.test.sensor.sensor.dto.AverageTemperaturePerFaceDTO;
import omc.test.sensor.sensor.dto.HourlyTemperatureSummaryDTO;
import omc.test.sensor.sensor.entites.SensorHistory;
import omc.test.sensor.sensor.services.SensorHistoryManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omc.test.sensor.sensor.entites.Sensor;
import omc.test.sensor.sensor.services.SensorManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sensorMonitor")
@Slf4j
@AllArgsConstructor
public class SensorMonitorController {

    private final SensorManager sensorManager;
    private final SensorHistoryManager sensorHistoryManager;

    @GetMapping("/view")
    public String viewSensors() {
        return "sensor";
    }

    @PostMapping("/data/batch")
    public ResponseEntity<String> receiveBatchedSensorData(@RequestBody List<Sensor> sensors) {
        log.info("Received batch of {} sensors", sensors.size());

        try {
            sensorManager.updateSensors(sensors);
            return ResponseEntity.ok("Batch received successfully");
        } catch (Exception e) {
            log.error("Unable to processing sensor data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing sensor data");
        }
    }

    @GetMapping("/addSensor")
    public ResponseEntity<String> addSensor(Sensor sensor) {
        try {
            sensorManager.addSensor(sensor);
            return ResponseEntity.ok("Sensor added successfully.");
        } catch (Exception e) {
            log.error("Unable to add sensorId: %d".formatted(sensor.getId()), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding sensor.");
        }
    }

    @PostMapping("/removeSensor")
    public ResponseEntity<String> removeSensor(@RequestParam Integer id) {
        try {
            sensorManager.removeSensor(id);
            return ResponseEntity.ok("Sensor removed successfully.");
        } catch (Exception e) {
            log.error("Error removing sensorId: %d".formatted(id), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing sensor.");
        }
    }


    @GetMapping("/getMalfunctioningSensors")
    public @ResponseBody List<Sensor> getMalfunctioningSensors() {
        try {
            return sensorManager.getMalfunctioningSensors().values().stream().toList();
        } catch (Exception e) {
            log.error("Unable to get getMalfunctioningSensors", e);

        }
        return null;
    }

    @GetMapping("/summarizeHourlyTemperatures")
    public @ResponseBody List<SensorHistory> summarizeHourlyTemperatures() {
        try {
            return sensorHistoryManager.summarizeHourlyTemperatures().stream().toList();
        } catch (Exception e) {
            log.error("Unable to get summarizeHourlyTemperatures", e);
        }
        return null;
    }

    @GetMapping("/summarizeHourlyTemperaturesForThePastWeek")
    public @ResponseBody List<HourlyTemperatureSummaryDTO> summarizeHourlyTemperaturesForThePastWeek() {
        try {
            return sensorHistoryManager.summarizeHourlyTemperaturesForPastWeek().stream().toList();
        } catch (Exception e) {
            log.error("Unable to get summarizeHourlyTemperaturesForThePastWeek", e);
        }
        return null;
    }

    @GetMapping("/reportAverageHourlyTemperature")
    public @ResponseBody List<AverageTemperaturePerFaceDTO> reportAverageHourlyTemperature() {
        try {
            return sensorManager.reportAverageHourlyTemperature();
        } catch (Exception e) {
            log.error("Unable to reportAverageHourlyTemperature", e);
        }
        return null;
    }

    @GetMapping("/getAvailableSensors")
    public String getAvailableSensors(Model model) {
        try {
            List<Sensor> sensors = sensorManager.getAvailableSensors();
            model.addAttribute("sensors", sensors); // Add the sensor list to the model
        } catch (Exception e) {
            log.error("Unable to get availableSensors", e);
        }
        return "availableSensors";
    }

    @PostMapping("/getSensors")
    public @ResponseBody List<Sensor> getAvailableSensors() {
        try {
            return sensorManager.getAvailableSensors();
        } catch (Exception e) {
            log.error("Unable to get sensors", e);
        }
        return null;
    }
}
