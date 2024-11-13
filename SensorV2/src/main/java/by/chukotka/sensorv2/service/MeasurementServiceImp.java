package by.chukotka.sensorv2.service;


import by.chukotka.sensorv2.exception.MeasurementNotFoundException;
import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImp implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Override
    public Measurement saveMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }
    @Override
    public Measurement findByIdMeasurement(Integer id) {
        return measurementRepository.findById(id)
                .orElseThrow(() -> new MeasurementNotFoundException("Measurement not found"));
    }
    @Override
    public List<Measurement> findAllMeasurement() {
        return measurementRepository.findAll();
    }

    @Override
    public Integer getByCountRain() {
      // return measurementRepository.findByCountRain();// - или так!!!
        return Math.toIntExact(measurementRepository.findAll().stream().filter(Measurement::isRaining).count()); //- можно и так!!!
    }
}
