package omc.test.sensor.sensor.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import omc.test.sensor.sensor.dto.HourlyTemperatureSummaryDTO;
import omc.test.sensor.sensor.entites.SensorHistory;
import omc.test.sensor.sensor.repositories.SensorHistoryRepository;
import omc.test.sensor.sensor.services.SensorHistoryManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SensorHistoryManagerImpl implements SensorHistoryManager {

    private final SensorHistoryRepository sensorHistoryRepository;

    @Override
    public void saveSensorHistory(SensorHistory sensorHistory) {
        sensorHistoryRepository.save(sensorHistory);
    }

    @Override
    public List<SensorHistory> summarizeHourlyTemperatures() {
        long endDate = System.currentTimeMillis();
        long startDate = endDate - 3600000;

        return sensorHistoryRepository.summarizeHourlyTemperatures(startDate, endDate);
    }

    @Override
    public List<HourlyTemperatureSummaryDTO> summarizeHourlyTemperaturesForPastWeek() {
        long endDate = System.currentTimeMillis();
        long startDate = endDate - (7 * 24 * 3600000);
        List<Object[]> summarized = sensorHistoryRepository.summarizeHourlyTemperaturesForPastWeek(startDate, endDate);
        List<HourlyTemperatureSummaryDTO> report = new ArrayList<>();


        for (Object[] row : summarized) {
            String hour = (String) row[0];  // First column is the hour
            double avgTemp = (Double) row[1];  // Second column is the average temperature
            double minTemp = (Double) row[2];  // Third column is the minimum temperature
            double maxTemp = (Double) row[3];  // Fourth column is the maximum temperature
            report.add(new HourlyTemperatureSummaryDTO(hour, avgTemp, minTemp, maxTemp));
        }

        return report;
    }
}
