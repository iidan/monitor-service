package omc.test.sensor.sensor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyTemperatureSummaryDTO {
    private String hour;
    private double avgTemp;
    private double minTemp;
    private double maxTemp;

    public HourlyTemperatureSummaryDTO(String hour, double avgTemp, double minTemp, double maxTemp) {
        this.hour = hour;
        this.avgTemp = avgTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }
}

