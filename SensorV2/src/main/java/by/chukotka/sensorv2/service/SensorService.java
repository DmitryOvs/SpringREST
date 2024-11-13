package by.chukotka.sensorv2.service;

import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.model.Sensor;
import java.util.List;

public interface SensorService {

    Sensor saveSensor(Sensor sensor);


    Sensor findByIdSensor(Integer id);


    List<Sensor> getAllSensors();


    Sensor findByNameSensor(String nameSensor);

}
