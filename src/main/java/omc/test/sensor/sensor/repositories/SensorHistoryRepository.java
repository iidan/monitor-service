package omc.test.sensor.sensor.repositories;

import omc.test.sensor.sensor.entites.SensorHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorHistoryRepository extends JpaRepository<SensorHistory, Long> {

    @Query("SELECT sh FROM SensorHistory sh WHERE sh.time >= :startDate AND sh.time <= :endDate ORDER BY sh.time")
    List<SensorHistory> summarizeHourlyTemperatures(@Param("startDate") long startDate, @Param("endDate") long endDate);

    @Query(value = """
                SELECT DATE_FORMAT(FROM_UNIXTIME(time / 1000), '%Y-%m-%d %H:00') AS hour,
                       AVG(temperature) AS avgTemp,
                       MIN(temperature) AS minTemp,
                       MAX(temperature) AS maxTemp
                FROM sensor_history
                WHERE time >= :startTime AND time <= :endTime
                GROUP BY hour
                ORDER BY hour
               """, nativeQuery = true)
    List<Object[]> summarizeHourlyTemperaturesForPastWeek(@Param("startTime") long startTime, @Param("endTime") long endTime);

}
