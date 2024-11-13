package by.chukotka.sensorv2.repositories;


import by.chukotka.sensorv2.model.Sensor;
import by.chukotka.sensorv2.util.DataUtilsSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

    @DataJpaTest
    public class SensorRepositoryTest {

        @Autowired
        SensorsRepository sensorsRepository;

        @BeforeEach
        void setUp() {
            sensorsRepository.deleteAll();
        }

        @Test
        @DisplayName("Test save sensor name")
        public void givenSensor_whenSaveValues_thenSensorIsCreate() {
            //given
            Sensor sensorToSave = DataUtilsSensor.getOneSTransient();
            //when
            Sensor savedSensor = sensorsRepository.save(sensorToSave);
            //then
            assertThat(savedSensor).isNotNull();
            assertThat(savedSensor.getIdSensor()).isNotNull();
        }

        @Test
        @DisplayName("Test get sensor by id")
        public void givenSensorCreate_whenGetId_thenSensorIsReturn() {
            //given
            Sensor sensorToSave = DataUtilsSensor.getOneSTransient();
            sensorsRepository.save(sensorToSave);
            //when
            Sensor savedSensor = sensorsRepository.findById(sensorToSave.getIdSensor()).orElse(null);
            //then
            assertThat(savedSensor).isNotNull();
            assertThat(savedSensor.getNameSensor()).isEqualTo("OneS");
        }

        @Test
        @DisplayName("Test sensor if not exist by id")
        public void givenSensorIsNotCreate_whenGetId_thenSensorIsEmpty() {
            //given

            //when
            Sensor savedSensor = sensorsRepository.findById(1).orElse(null);
            //then
            assertThat(savedSensor).isNull();
        }

        @Test
        @DisplayName("Test get sensor by name")
        public void givenSensorCreate_whenGetName_thenSensorIsReturn() {
            //given
            Sensor sensorToSave = DataUtilsSensor.getTwoSTransient();
            sensorsRepository.save(sensorToSave);
            //when
            Sensor savedSensor = sensorsRepository.findByNameSensor("TwoS");
            //then
            assertThat(savedSensor).isNotNull();
            assertThat(savedSensor.getNameSensor()).isEqualTo("TwoS");
        }

        @Test
        @DisplayName("Test get all sensors by id")
        public void givenThreeSensorsCreate_whenFindAll_thenAllSensorsIsReturn() {
            //given
            Sensor sensorToSave1 = DataUtilsSensor.getOneSTransient();
            Sensor sensorToSave2 = DataUtilsSensor.getTwoSTransient();
            Sensor sensorToSave3 = DataUtilsSensor.getThreeSTransient();

            sensorsRepository.saveAll(List.of(sensorToSave1, sensorToSave2, sensorToSave3));
            //when
            List<Sensor> existingSensors = sensorsRepository.findAll();
            //then
            assertThat(CollectionUtils.isEmpty(existingSensors)).isFalse();
        }
    }


