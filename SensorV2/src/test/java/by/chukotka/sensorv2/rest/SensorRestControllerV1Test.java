package by.chukotka.sensorv2.rest;


import by.chukotka.sensorv2.DTO.SensorDTO;
import by.chukotka.sensorv2.exception.SensorNotFoundException;
import by.chukotka.sensorv2.exception.SensorWithDuplicateNameSensorException;
import by.chukotka.sensorv2.model.Sensor;
import by.chukotka.sensorv2.repositories.MeasurementRepository;
import by.chukotka.sensorv2.repositories.SensorsRepository;
import by.chukotka.sensorv2.service.MeasurementServiceImp;
import by.chukotka.sensorv2.service.SensorServiceImp;
import by.chukotka.sensorv2.util.DataUtilsSensor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest
public class SensorRestControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MeasurementRepository measurementRepository;
    @MockBean
    private MeasurementServiceImp measurementServiceImp;

    @MockBean
    private SensorsRepository sensorsRepository;
    @MockBean
    private SensorServiceImp sensorServiceImp;

    @Test
    @DisplayName("Test create sensor functionality")
    public void givenSensorDto_whenCreateSensor_thenSuccessResponse() throws Exception {
        //given
        SensorDTO dto = DataUtilsSensor.getOneSTransientDTO();
        Sensor entity = DataUtilsSensor.getOneSPersisted();
        BDDMockito.given(sensorServiceImp.saveSensor(any(Sensor.class)))
                .willReturn(entity);
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sensor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idSensor", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameSensor", CoreMatchers.is("OneS")));
    }

    @Test
    @DisplayName("Test create sensor with duplicate name functionality")
    public void givenSensorDtoWithDuplicateName_whenCreateSensor_thenErrorResponse() throws Exception {
        //given
        SensorDTO dto = DataUtilsSensor.getThreeSTransientDTO();
        BDDMockito.given(sensorServiceImp.saveSensor(any(Sensor.class)))
                .willThrow(new SensorWithDuplicateNameSensorException("Sensor with defined name is already exists"));
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sensor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Sensor with defined name is already exists")));
    }

    @Test
    @DisplayName("Test get sensor by id functionality")
    public void givenId_whenGetById_thenSuccessResponse() throws Exception {
        //given
        BDDMockito.given(sensorServiceImp.findByIdSensor(anyInt()))
                .willReturn(DataUtilsSensor.getTwoSPersisted());
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/sensor/2")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idSensor", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameSensor", CoreMatchers.is("TwoS")));
    }

    @Test
    @DisplayName("Test get sensor by incorrect id functionality")
    public void givenIncorrectId_whenGetById_thenErrorResponse() throws Exception {
        //given
        BDDMockito.given(sensorServiceImp.findByIdSensor(anyInt()))
                .willThrow(new SensorNotFoundException("Sensor not found"));
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/sensor/1")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Sensor not found")));
    }


}