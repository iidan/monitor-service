package omc.test.sensor.sensor.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AverageTemperaturePerFaceDTO {
    // Getters
    private String face;
    private double avgTemp;

    public AverageTemperaturePerFaceDTO() {
    }

    public AverageTemperaturePerFaceDTO(String face, double avgTemp) {
        this.face = face;
        this.avgTemp = avgTemp;
    }

}