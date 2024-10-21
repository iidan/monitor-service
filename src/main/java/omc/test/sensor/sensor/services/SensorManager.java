package omc.test.sensor.sensor.services;

import jakarta.transaction.Transactional;
import omc.test.sensor.sensor.dto.AverageTemperaturePerFaceDTO;
import omc.test.sensor.sensor.entites.Sensor;
import omc.test.sensor.sensor.enums.BuildingFaceEnum;

import java.util.List;
import java.util.Map;

public interface SensorManager {
    void addSensor(Sensor sensor);

    @Transactional
    void removeSensor(int id);

    @Transactional
    void updateSensors(List<Sensor> sensors);

    Map<Integer, Sensor> getMalfunctioningSensors();

    double getAverageTemperature(BuildingFaceEnum buildingFaceEnum);

    void deleteInactiveSensors(long thresholdTime);

    List<AverageTemperaturePerFaceDTO> reportAverageHourlyTemperature();

    List<Sensor> getAvailableSensors();

    List<Sensor> getSensorsByBuildingSide(BuildingFaceEnum buildingFaceEnum);
}
