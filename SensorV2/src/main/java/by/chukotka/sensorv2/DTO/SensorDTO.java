package by.chukotka.sensorv2.DTO;


import by.chukotka.sensorv2.model.Sensor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO {

    private int idSensor;
    private String nameSensor;


    public Sensor toEntity() {
        return Sensor.builder()
                .idSensor(idSensor)
                .nameSensor(nameSensor)
                .build();
    }

    public static SensorDTO fromEntity(Sensor sensor) {
        return SensorDTO.builder()
                .idSensor(sensor.getIdSensor())
                .nameSensor(sensor.getNameSensor())
                .build();
    }
}
