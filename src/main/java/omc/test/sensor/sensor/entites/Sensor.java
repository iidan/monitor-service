package omc.test.sensor.sensor.entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private long time;
    private String face;
    private double temperature;

    public Sensor() {
    }

    public Sensor(long time, String face, double temperature) {
        this.time = time;
        this.face = face;
        this.temperature = temperature;
    }
}
