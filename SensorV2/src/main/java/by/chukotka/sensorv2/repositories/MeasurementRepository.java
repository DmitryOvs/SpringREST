package by.chukotka.sensorv2.repositories;

import by.chukotka.sensorv2.model.Measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {


    @Query("SELECT COUNT(*) FROM Measurement m WHERE m.isRaining = true")
    Integer findByCountRain();

}