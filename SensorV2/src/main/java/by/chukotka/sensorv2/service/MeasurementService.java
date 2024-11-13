package by.chukotka.sensorv2.service;

import by.chukotka.sensorv2.model.Measurement;

import java.util.List;

public interface MeasurementService {

    Measurement saveMeasurement(Measurement measurement);

    List<Measurement> findAllMeasurement();

    Integer getByCountRain();

    Measurement findByIdMeasurement(Integer id);

}
