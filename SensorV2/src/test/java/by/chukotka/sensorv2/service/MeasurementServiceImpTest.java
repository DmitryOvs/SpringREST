package by.chukotka.sensorv2.service;

import by.chukotka.sensorv2.exception.MeasurementNotFoundException;
import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.repositories.MeasurementRepository;
import by.chukotka.sensorv2.util.DataUtilsMeasurement;
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
public class MeasurementServiceImpTest {

    @Mock
    private MeasurementRepository measurementRepository;
    @InjectMocks
    private MeasurementServiceImp measurementServiceImp;


    @Test
    @DisplayName("Test save measurement functionality")
    public void givenMeasurementToSave_whenSaveMeasurement_thenRepositoryIsCalled() {
        //given
        Measurement measurementToSave = DataUtilsMeasurement.getOneTransient();
        BDDMockito.given(measurementRepository.save(any(Measurement.class))).willReturn(measurementToSave);
        //when
        Measurement savedMeasurement = measurementServiceImp.saveMeasurement(measurementToSave);
        //then
        assertThat(savedMeasurement).isNotNull();
    }

    @Test
    @DisplayName("Test get measurement by id functionality")
    public void givenMeasurement_whenGetIdMeasurement_thenMeasurementIsReturn() {
        //given
        BDDMockito.given(measurementRepository.findById(anyInt()))
                .willReturn(Optional.of(DataUtilsMeasurement.getOnePersisted()));
        //when
        Measurement existMeasurement = measurementServiceImp.findByIdMeasurement(1);
        //then
        assertThat(existMeasurement).isNotNull();
    }

    @Test
    @DisplayName("Test get measurement by id functionality")
    public void givenIncorrectId_whenGetById_thenExceptionIsThrown() {
        //given
        BDDMockito.given(measurementRepository.findById(anyInt()))
                .willThrow(MeasurementNotFoundException.class);
        //when
        assertThrows(MeasurementNotFoundException.class, () -> measurementServiceImp.findByIdMeasurement(1));
        //then

    }

    @Test
    @DisplayName("Test get all measurements functionality")
    public void givenAllMeasurements_whenGetAllMeasurements_thenMeasurementIsReturn() {
        //given
        Measurement measurement1 = DataUtilsMeasurement.getOneTransient();
        Measurement measurement2 = DataUtilsMeasurement.getTwoTransient();
        Measurement measurement3 = DataUtilsMeasurement.getThreeTransient();
        List<Measurement> measurements = List.of(measurement1, measurement2, measurement3);

        BDDMockito.given(measurementRepository.findAll()).willReturn(measurements);
        //when
        List<Measurement> allMeasurement = measurementServiceImp.findAllMeasurement();
        //then
        assertThat(CollectionUtils.isEmpty(allMeasurement)).isFalse();
        assertThat(allMeasurement.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test get count measurement rain")
    public void givenMeasurements_whenCountMeasurements_thenMeasurementIsReturn() {
        //given
        Measurement measurement1 = DataUtilsMeasurement.getOneTransient();
        Measurement measurement2 = DataUtilsMeasurement.getTwoTransient();
        Measurement measurement3 = DataUtilsMeasurement.getThreeTransient();
        List<Measurement> measurement = List.of(measurement1, measurement2, measurement3);

        BDDMockito.given(measurementRepository.findAll()).willReturn(measurement);
        //when
        Integer countRain = measurementServiceImp.getByCountRain();
        System.out.println(measurement);
        //then
        assertThat(countRain).isEqualTo(1);
    }
}
