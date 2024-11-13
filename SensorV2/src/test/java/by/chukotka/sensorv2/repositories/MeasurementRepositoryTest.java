package by.chukotka.sensorv2.repositories;

import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.util.DataUtilsMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
public class MeasurementRepositoryTest {

    @Autowired
    MeasurementRepository measurementRepository;

    @BeforeEach
    void setUp() {
        measurementRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save measurement values")
    public void givenMeasurement_whenSaveValues_thenMeasurementIsCreate() {
        //given
        Measurement measurementToSave = DataUtilsMeasurement.getOneTransient();
        //when
        Measurement savedMeasurement = measurementRepository.save(measurementToSave);
        //then
        assertThat(savedMeasurement).isNotNull();
        assertThat(savedMeasurement.getIdMeasurement()).isNotNull();
    }

    @Test
    @DisplayName("Test get measurement by id values")
    public void givenMeasurementCreated_whenGetById_thenMeasurementIsReturn() {
        //given
        Measurement measurementToSave = DataUtilsMeasurement.getOneTransient();
        measurementRepository.save(measurementToSave);
        //when
        Measurement existMeasurement = measurementRepository.findById(measurementToSave.getIdMeasurement()).orElse(null);
        //then
        assertThat(existMeasurement).isNotNull();
         assertThat(existMeasurement.getTemperature()).isEqualTo(-25);
    }

    @Test
    @DisplayName("Test measurement not found values")
    public void givenMeasurementIsNotCreated_whenGetById_thenOptionalIsEmpty() {
        //given

        //when
        Measurement existMeasurement = measurementRepository.findById(1).orElse(null);
        //then
        assertThat(existMeasurement).isNull();
    }

    @Test
    @DisplayName("Test get all measurements values")
    public void givenAllMeasurement_whenFindAll_thenAllMeasurementAreReturn() {
        //given
        Measurement measurementToSave1 = DataUtilsMeasurement.getOneTransient();
        Measurement measurementToSave2 = DataUtilsMeasurement.getTwoTransient();
        Measurement measurementToSave3 = DataUtilsMeasurement.getThreeTransient();

        measurementRepository.saveAll(List.of(measurementToSave1,measurementToSave2,measurementToSave3));
        //when
        List<Measurement> existMeasurements = measurementRepository.findAll();
        //then
        assertThat(CollectionUtils.isEmpty(existMeasurements)).isFalse();
    }

    @Test
    @DisplayName("Test get how much measurements training")
    public void givenAllMeasurementAndOneRain_whenFindAllRain_thenReturnOnlyOneMeasurement() {
        //given
        Measurement measurementToSave1 = DataUtilsMeasurement.getOneTransient();
        Measurement measurementToSave2 = DataUtilsMeasurement.getTwoTransient();
        Measurement measurementToSave3 = DataUtilsMeasurement.getThreeTransient();

        measurementRepository.saveAll(List.of(measurementToSave1, measurementToSave2, measurementToSave3));
        //when
        Integer existMeasurements = measurementRepository.findByCountRain();
        //then
        assertThat(existMeasurements).isEqualTo(1);
    }
}
