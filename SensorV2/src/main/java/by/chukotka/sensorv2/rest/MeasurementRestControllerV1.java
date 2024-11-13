package by.chukotka.sensorv2.rest;

import by.chukotka.sensorv2.DTO.MeasurementDTO;
import by.chukotka.sensorv2.exception.ErrorDto;
import by.chukotka.sensorv2.exception.MeasurementNotFoundException;
import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.service.MeasurementService;
import by.chukotka.sensorv2.exception.SensorIncorrectValueException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/measurement")
@RequiredArgsConstructor
public class MeasurementRestControllerV1 {

    private final MeasurementService measurementService;

    @PostMapping
    public ResponseEntity<?> createMeasurement(@RequestBody MeasurementDTO dto) {
        Measurement entity = dto.toEntity();
        Measurement createdMeasurement = measurementService.saveMeasurement(entity);
        MeasurementDTO result = MeasurementDTO.fromEntity(createdMeasurement);
        return ResponseEntity.ok(result);
    }


    @GetMapping
    public ResponseEntity<?> getAllMeasurements() {
        List<Measurement> entities = measurementService.findAllMeasurement();
        List<MeasurementDTO> dtos = entities.stream()
                .map(MeasurementDTO::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasurementById(@PathVariable int id) {
        try {
        Measurement entity = measurementService.findByIdMeasurement(id);
        MeasurementDTO result = MeasurementDTO.fromEntity(entity);
        return ResponseEntity.ok(result);
    } catch (MeasurementNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(ErrorDto.builder()
                        .status(404)
                        .message(e.getMessage())
                        .build());
    }
}

    @GetMapping("/rainyDaysCount")
    public Integer findByCountRain() {
        return measurementService.getByCountRain();
    }

}

