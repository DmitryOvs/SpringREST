package by.chukotka.sensorv2.DTO;

import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.model.Sensor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


import java.util.Date;

@Data
@Builder
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDTO {

    private int idMeasurement;
    private boolean isRaining;
    private float temperature;
    private Date createDate;
    private Sensor senS;


    public Measurement toEntity() {
        return Measurement.builder()
                .idMeasurement(idMeasurement)
                .isRaining(isRaining)
                .temperature(temperature)
                .createDate(createDate)
                .senS(senS)
                .build();
    }

    public static MeasurementDTO fromEntity(Measurement measurement) {
        return MeasurementDTO.builder()
                .idMeasurement(measurement.getIdMeasurement())
                .isRaining(measurement.isRaining())
                .temperature(measurement.getTemperature())
                .createDate(measurement.getCreateDate())
                .senS(measurement.getSenS())
                .build();
    }
}

