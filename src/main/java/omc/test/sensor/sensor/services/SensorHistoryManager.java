package omc.test.sensor.sensor.services;

import jakarta.transaction.Transactional;
import omc.test.sensor.sensor.dto.HourlyTemperatureSummaryDTO;
import omc.test.sensor.sensor.entites.SensorHistory;

import java.util.List;

public interface SensorHistoryManager {

    @Transactional
    void saveSensorHistory(SensorHistory sensorHistory);

    List<SensorHistory> summarizeHourlyTemperatures();

    List<HourlyTemperatureSummaryDTO> summarizeHourlyTemperaturesForPastWeek();

}
