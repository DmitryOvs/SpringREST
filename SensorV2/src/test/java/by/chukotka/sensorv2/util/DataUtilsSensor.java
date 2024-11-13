package by.chukotka.sensorv2.util;

import by.chukotka.sensorv2.DTO.SensorDTO;
import by.chukotka.sensorv2.model.Sensor;

public class DataUtilsSensor {

    public static Sensor getOneSTransient(){
    return Sensor.builder()
            .nameSensor("OneS")
            .build();
}

    public static Sensor getTwoSTransient(){
        return Sensor.builder()
                .nameSensor("TwoS")
                .build();
    }

    public static Sensor getThreeSTransient(){
        return Sensor.builder()
                .nameSensor("ThreeS")
                .build();
    }

    public static Sensor getOneSPersisted(){
        return Sensor.builder()
                .idSensor(1)
                .nameSensor("OneS")
                .build();
    }

    public static Sensor getTwoSPersisted(){
        return Sensor.builder()
                .idSensor(2)
                .nameSensor("TwoS")
                .build();
    }

    public static Sensor getThreeSPersisted(){
        return Sensor.builder()
                .idSensor(3)
                .nameSensor("ThreeS")
                .build();
    }

    public static SensorDTO getOneSTransientDTO(){
        return SensorDTO.builder()
                .nameSensor("OneS")
                .build();
    }

    public static SensorDTO getTwoSTransientDTO(){
        return SensorDTO.builder()
                .nameSensor("TwoS")
                .build();
    }

    public static SensorDTO getThreeSTransientDTO(){
        return SensorDTO.builder()
                .nameSensor("ThreeS")
                .build();
    }

    public static SensorDTO getOneSPersistedDTO(){
        return SensorDTO.builder()
                .idSensor(1)
                .nameSensor("OneS")
                .build();
    }

    public static SensorDTO getTwoSPersistedDTO(){
        return SensorDTO.builder()
                .idSensor(2)
                .nameSensor("TwoS")
                .build();
    }

    public static SensorDTO getThreeSPersistedDTO(){
        return SensorDTO.builder()
                .idSensor(3)
                .nameSensor("ThreeS")
                .build();
    }
}
