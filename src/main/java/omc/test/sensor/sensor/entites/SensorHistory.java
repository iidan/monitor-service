    package omc.test.sensor.sensor.entites;

    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.experimental.Accessors;

    @Entity
    @Data
    @Accessors(chain = true)
    @Table(name = "Sensor_history")
    public class SensorHistory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private long time;  // Timestamp for the reading
        private double temperature;  // Temperature reading at that hour
        private Integer sensorId;

        public SensorHistory() {

        }

        public SensorHistory(Sensor sensor) {
            this.sensorId = sensor.getId();
            this.time = sensor.getTime();
            this.temperature = sensor.getTemperature();
        }
    }
