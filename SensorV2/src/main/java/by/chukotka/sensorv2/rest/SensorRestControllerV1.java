package by.chukotka.sensorv2.rest;

import by.chukotka.sensorv2.DTO.SensorDTO;
import by.chukotka.sensorv2.exception.ErrorDto;
import by.chukotka.sensorv2.exception.SensorNotFoundException;
import by.chukotka.sensorv2.exception.SensorWithDuplicateNameSensorException;
import by.chukotka.sensorv2.model.Sensor;
import by.chukotka.sensorv2.service.SensorService;
import by.chukotka.sensorv2.service.SensorServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensor")
@RequiredArgsConstructor
public class SensorRestControllerV1 {

    private final SensorService sensorService;

    @PostMapping
    public ResponseEntity<?> createSensor(@RequestBody SensorDTO dto) {
        try {
            Sensor entity = dto.toEntity();
            Sensor createSensor = sensorService.saveSensor(entity);
            SensorDTO createDto = SensorDTO.fromEntity(createSensor);
            return ResponseEntity.ok(createDto);
        } catch (SensorWithDuplicateNameSensorException e) {
            return ResponseEntity.badRequest()
                    .body(ErrorDto.builder()
                            .status(400)
                            .message(e.getMessage())
                            .build());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSensorById(@PathVariable Integer id) {
        try {
            Sensor entity = sensorService.findByIdSensor(id);
            SensorDTO result = SensorDTO.fromEntity(entity);
            return ResponseEntity.ok(result);
        } catch (SensorNotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(ErrorDto.builder()
                            .status(404)
                            .message(e.getMessage())
                            .build());
       }
        }

    @GetMapping
    public ResponseEntity<?> getAllSensors() {
        List<Sensor> entities = sensorService.getAllSensors();
        List<SensorDTO> dtos = entities.stream()
                .map(SensorDTO::fromEntity).toList();
        return ResponseEntity.ok(dtos);
    }
}

