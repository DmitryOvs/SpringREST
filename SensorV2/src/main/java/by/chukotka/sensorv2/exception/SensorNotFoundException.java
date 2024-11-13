package by.chukotka.sensorv2.exception;

public class SensorNotFoundException extends RuntimeException{

    public SensorNotFoundException(String massage) {
        super(massage);
    }
}
