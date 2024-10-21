package omc.test.sensor.sensor.repositories;

import jakarta.transaction.Transactional;
import omc.test.sensor.sensor.entites.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Sensor s WHERE s.time < :thresholdTime")
    void deleteInactiveSensors(long thresholdTime);

    @Query(value = """
                    SELECT face, AVG(temperature) AS avgTemp
                    FROM sensor
                    WHERE time >= :startTime AND time <= :endTime
                    GROUP BY face
                    """, nativeQuery = true)
    List<Object[]> averageHourlyTemperaturePerFace(@Param("startTime") long startTime, @Param("endTime") long endTime);

    List<Sensor> findSensorsByFace(String face);

}
