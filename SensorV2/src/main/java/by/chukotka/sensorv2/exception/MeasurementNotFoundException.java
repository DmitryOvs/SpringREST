package by.chukotka.sensorv2.exception;

public class MeasurementNotFoundException extends RuntimeException{
    public MeasurementNotFoundException(String message) {
        super(message);
    }
}
