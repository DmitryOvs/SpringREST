package by.chukotka.sensorv2.service;



import by.chukotka.sensorv2.exception.SensorNotFoundException;
import by.chukotka.sensorv2.exception.SensorWithDuplicateNameSensorException;
import by.chukotka.sensorv2.model.Sensor;
import by.chukotka.sensorv2.repositories.SensorsRepository;
import by.chukotka.sensorv2.util.DataUtilsSensor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImpTest {

    @Mock
    private SensorsRepository sensorsRepository;
    @InjectMocks
    private SensorServiceImp sensorService;

    @Test
    @DisplayName("Test save sensor functionality")
    public void givenSensorToSave_whenSaveSensor_thenSaveSensorIsCalled() {
        //given
        Sensor saveSensor = DataUtilsSensor.getOneSTransient();

        BDDMockito.given(sensorsRepository.findByNameSensor(anyString()))
                .willReturn(null);
        BDDMockito.given(sensorsRepository.save(any(Sensor.class)))
                .willReturn(DataUtilsSensor.getOneSPersisted());
        //when
        Sensor savedToSensor = sensorService.saveSensor(saveSensor);
        //then
        assertThat(savedToSensor).isNotNull();
    }

    @Test
    @DisplayName("Test save sensor with duplicate name functionality")
    public void givenSensorToSaveWithDuplicateName_whenSaveSensor_thenExceptionIsThrown() {
        //given
        Sensor saveSensor = DataUtilsSensor.getOneSTransient();
        BDDMockito.given(sensorsRepository.findByNameSensor(anyString()))
                .willReturn(DataUtilsSensor.getOneSPersisted());
        //when
        assertThrows(SensorWithDuplicateNameSensorException.class, () -> sensorService.saveSensor(saveSensor));
        //then
        verify(sensorsRepository, never()).save(any(Sensor.class));
    }


    @Test
    @DisplayName("Test get sensor by id functionality")
    public void givenSensor_whenGetIdSensor_thenSensorIsReturn() {
        //given
        BDDMockito.given(sensorsRepository.findById(anyInt()))
                .willReturn(Optional.ofNullable(DataUtilsSensor.getOneSPersisted()));
        //when
        Sensor existSensor = sensorService.findByIdSensor(1);
        //then
        assertThat(existSensor).isNotNull();
    }

    @Test
    @DisplayName("Test get sensor by id functionality")
    public void givenIncorrectId_whenGetById_thenExceptionIsThrown() {
        //given
        BDDMockito.given(sensorsRepository.findById(anyInt()))
                .willThrow(SensorNotFoundException.class);
        //when
        assertThrows(SensorNotFoundException.class, () -> sensorService.findByIdSensor(1));
        //then

    }

    @Test
    @DisplayName("Test get all sensors functionality")
    public void givenAllSensors_whenGetAllSensors_thenSensorIsReturn() {
        //given
        Sensor sensor1 = DataUtilsSensor.getOneSTransient();
        Sensor sensor2 = DataUtilsSensor.getTwoSTransient();
        Sensor sensor3 = DataUtilsSensor.getThreeSTransient();
        List<Sensor> sensors = List.of(sensor3, sensor2, sensor1);

        BDDMockito.given(sensorsRepository.findAll()).willReturn(sensors);
        //when
        List<Sensor> allSensors = sensorService.getAllSensors();
        //then
        assertThat(CollectionUtils.isEmpty(allSensors)).isFalse();
        assertThat(allSensors.size()).isEqualTo(3);
    }
}

