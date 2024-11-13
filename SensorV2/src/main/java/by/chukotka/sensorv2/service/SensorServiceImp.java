package by.chukotka.sensorv2.service;


import by.chukotka.sensorv2.exception.SensorNotFoundException;
import by.chukotka.sensorv2.exception.SensorWithDuplicateNameSensorException;
import by.chukotka.sensorv2.model.Sensor;
import by.chukotka.sensorv2.repositories.SensorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SensorServiceImp implements SensorService {

    SensorsRepository sensorsRepository;

    @Override
    public Sensor saveSensor(Sensor sensor) {
        Sensor duplicateSensor = sensorsRepository.findByNameSensor(sensor.getNameSensor());
        if (Objects.nonNull(duplicateSensor)) {
            throw new SensorWithDuplicateNameSensorException("Sensor with definer is name already exists");
        }
        return sensorsRepository.save(sensor);
    }
    @Override
    public Sensor findByIdSensor(Integer id) {
        return sensorsRepository.findById(id).orElseThrow(() -> new SensorNotFoundException("Sensor not found"));
    }

    @Override
    public List<Sensor> getAllSensors() {
        return sensorsRepository.findAll();
    }

    @Override
    public Sensor findByNameSensor(String name) {
        Sensor existSensor = sensorsRepository.findByNameSensor(name);

        if (Objects.isNull(existSensor)) {
            throw new SensorNotFoundException("Sensor with name " + name + " not found");
        }
        return existSensor;
    }
}
