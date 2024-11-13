package by.chukotka.sensorv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(name = "id_sensor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSensor;

    @Column(name = "name_sensor")
    private String nameSensor;

    @OneToMany(mappedBy = "senS")
    private List<Measurement> allMeasurements;
}

