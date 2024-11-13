package by.chukotka.sensorv2.util;

import by.chukotka.sensorv2.DTO.MeasurementDTO;
import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.model.Sensor;



public class DataUtilsMeasurement {

    public static Measurement getOneTransient(){
        return Measurement.builder()
                .isRaining(false)
                .temperature(-25)
                .senS(null)
                .build();
    }

    public static Measurement getTwoTransient(){
        return Measurement.builder()
                .isRaining(true)
                .temperature(39)
                .senS(null)
                .build();
    }

    public static Measurement getThreeTransient(){
        return Measurement.builder()
                .isRaining(false)
                .temperature(76)
                .senS(null)
                .build();
    }

    public static Measurement getOnePersisted(){
        return Measurement.builder()
                .idMeasurement(1)
                .isRaining(false)
                .temperature(-25)
                .senS(Sensor.builder()
                        .nameSensor("One")
                        .build())
                .build();
    }

    public static Measurement getTwoPersisted(){
        return Measurement.builder()
                .idMeasurement(2)
                .isRaining(true)
                .temperature(39)
                .senS(Sensor.builder()
                        .nameSensor("Two")
                        .build())
                .build();
    }

    public static Measurement getThreePersisted(){
        return Measurement.builder()
                .idMeasurement(3)
                .isRaining(false)
                .temperature(76)
                .senS(Sensor.builder()
                        .nameSensor("Three")
                        .build())
                .build();
    }

    public static MeasurementDTO getOneDTOTransient(){
        return MeasurementDTO.builder()
                .isRaining(false)
                .temperature(-25)
                .senS(null)
                .build();
    }

    public static MeasurementDTO getTwoDTOTransient(){
        return MeasurementDTO.builder()
                .isRaining(true)
                .temperature(39)
                .senS(null)
                .build();
    }

    public static MeasurementDTO getThreeDTOTransient(){
        return MeasurementDTO.builder()
                .isRaining(false)
                .temperature(76)
                .senS(null)
                .build();
    }

    public static MeasurementDTO getOneDTOPersisted(){
        return MeasurementDTO.builder()
                .idMeasurement(1)
                .isRaining(false)
                .temperature(-25)
                .senS(Sensor.builder()
                        .nameSensor("One")
                        .build())
                .build();
    }

    public static MeasurementDTO getTwoDTOPersisted(){
        return MeasurementDTO.builder()
                .idMeasurement(2)
                .isRaining(true)
                .temperature(39)
                .senS(Sensor.builder()
                        .nameSensor("Two")
                        .build())
                .build();
    }

    public static MeasurementDTO getThreeDTOPersisted(){
        return MeasurementDTO.builder()
                .idMeasurement(3)
                .isRaining(false)
                .temperature(76)
                .senS(Sensor.builder()
                        .nameSensor("Three")
                        .build())
                .build();
    }
}
