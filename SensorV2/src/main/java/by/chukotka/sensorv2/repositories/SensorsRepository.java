package by.chukotka.sensorv2.repositories;

import by.chukotka.sensorv2.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Sensor findByNameSensor(String nameSensor);
}
