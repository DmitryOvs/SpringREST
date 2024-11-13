package by.chukotka.sensorv2.It;

import by.chukotka.sensorv2.DTO.SensorDTO;
import by.chukotka.sensorv2.model.Sensor;
import by.chukotka.sensorv2.repositories.SensorsRepository;
import by.chukotka.sensorv2.util.DataUtilsSensor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
//@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItSensorRestControllerV1Test //extends AbstractRestControllerBaseTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SensorsRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }


    @Test
    @DisplayName("Test create sensor functionality")
    public void givenSensorDto_whenCreateSensor_thenSuccessResponse() throws Exception {
        //given
        SensorDTO dto = DataUtilsSensor.getOneSTransientDTO();
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
        String duplicateName = "DuplicateName";
        Sensor sensor = DataUtilsSensor.getOneSTransient();
        sensor.setNameSensor(duplicateName);
        repository.save(sensor);
        SensorDTO dto = DataUtilsSensor.getOneSTransientDTO();
        dto.setNameSensor(duplicateName);
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
        Sensor sensor = DataUtilsSensor.getTwoSTransient();
        repository.save(sensor);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/sensor/" + sensor.getIdSensor())
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

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/sensor/2")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Sensor not found")));
    }
    }
