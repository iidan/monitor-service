package omc.test.sensor.sensor.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omc.test.sensor.sensor.api.SensorApiService;
import omc.test.sensor.sensor.dto.AverageTemperaturePerFaceDTO;
import omc.test.sensor.sensor.enums.BuildingFaceEnum;
import omc.test.sensor.sensor.entites.Sensor;
import omc.test.sensor.sensor.repositories.SensorRepository;
import omc.test.sensor.sensor.services.SensorManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class SensorManagerImpl implements SensorManager {
    private final SensorRepository sensorRepository;
    private final SensorApiService sensorApiService;

    @Override
    public List<Sensor> getAvailableSensors() {
        return sensorRepository.findAll();
    }

    @Override
    public void updateSensors(List<Sensor> sensors) {
        if (!sensors.isEmpty()) {
            sensorRepository.saveAll(sensors);
            log.info("Updated {} sensors successfully", sensors.size());
        } else {
            log.warn("No sensors to update");
        }
    }

    @Override
    public void addSensor(Sensor sensor) {
        sensor = sensorRepository.save(sensor);
        sensor.setTime(System.currentTimeMillis());
        sensorApiService.addSensor(sensor);
        log.info("Sensor added: {}", sensor.getId());
    }

    @Override
    public void removeSensor(int id) {
        Optional<Sensor> sensor = sensorRepository.findById((long) id);

        if (sensor.isPresent()) {
            sensorApiService.removeSensor(sensor.get());
            sensorRepository.delete(sensor.get());
            log.info("Sensor removed: {}", id);
        }
    }

    @Override
    public Map<Integer, Sensor> getMalfunctioningSensors() {
        Map<Integer, Sensor> malfunctioningSensors = new HashMap<>();

        for (BuildingFaceEnum face : BuildingFaceEnum.values()) {
            double averageTemperatureBySide = getAverageTemperature(face);
            List<Sensor> sensorsBySide = getSensorsByBuildingSide(face);

            sensorsBySide.stream()
                    .filter(sensor -> sensor.getTemperature() > averageTemperatureBySide)
                    .forEach(sensor -> malfunctioningSensors.put(sensor.getId(), sensor));
        }

        return malfunctioningSensors;
    }

    @Override
    public double getAverageTemperature(BuildingFaceEnum buildingFaceEnum) {
        double average = 0.0;

        List<Sensor> sensors = getSensorsByBuildingSide(buildingFaceEnum);
        for (Sensor sensor : sensors) {
            average += sensor.getTemperature();
        }

        return average / sensors.size();
    }

    @Override
    public void deleteInactiveSensors(long thresholdTime) {
        sensorRepository.deleteInactiveSensors(thresholdTime);
    }

    @Override
    public List<AverageTemperaturePerFaceDTO> reportAverageHourlyTemperature() {
        long endTime = System.currentTimeMillis(); // Current time in milliseconds
        long startTime = endTime - 3600000; // One hour before

        List<Object[]> results = sensorRepository.averageHourlyTemperaturePerFace(startTime, endTime);
        List<AverageTemperaturePerFaceDTO> averages = new ArrayList<>();

        for (Object[] row : results) {
            String face = (String) row[0]; // Assuming face is the first column
            double avgTemp = (Double) row[1]; // Assuming avgTemp is the second column
            averages.add(new AverageTemperaturePerFaceDTO(face, avgTemp));
        }

        return averages;
    }

    @Override
    public List<Sensor> getSensorsByBuildingSide(BuildingFaceEnum buildingFaceEnum) {
        return sensorRepository.findSensorsByFace(buildingFaceEnum.name());
    }
}