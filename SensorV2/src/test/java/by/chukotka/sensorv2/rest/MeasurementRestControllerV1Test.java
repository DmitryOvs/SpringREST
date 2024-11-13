package by.chukotka.sensorv2.rest;

import by.chukotka.sensorv2.DTO.MeasurementDTO;
import by.chukotka.sensorv2.exception.MeasurementNotFoundException;
import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.repositories.MeasurementRepository;
import by.chukotka.sensorv2.repositories.SensorsRepository;
import by.chukotka.sensorv2.service.MeasurementServiceImp;
import by.chukotka.sensorv2.service.SensorServiceImp;
import by.chukotka.sensorv2.util.DataUtilsMeasurement;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.hamcrest.Matchers.is;


@WebMvcTest
public class MeasurementRestControllerV1Test {

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
    @DisplayName("Test create measurement functionality")
    public void givenMeasurementDto_whenCreateDeveloper_thenSuccessResponse() throws Exception {
        //given
        MeasurementDTO dto = DataUtilsMeasurement.getOneDTOTransient();
        Measurement entity = DataUtilsMeasurement.getOnePersisted();
        BDDMockito.given(measurementServiceImp.saveMeasurement(any(Measurement.class)))
                .willReturn(entity);
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/measurement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idMeasurement", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raining", CoreMatchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature", CoreMatchers.is(-25.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.senS", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Test get measurement by id functionality")
    public void givenId_whenGetById_thenSuccessResponse() throws Exception {
        //given
        BDDMockito.given(measurementServiceImp.findByIdMeasurement(anyInt()))
                .willReturn(DataUtilsMeasurement.getTwoPersisted());
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurement/2")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idMeasurement", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.raining", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature", CoreMatchers.is(39.0)));
    }

    @Test
    @DisplayName("Test get measurement by incorrect id functionality")
    public void givenIncorrectId_whenGetById_thenErrorResponse() throws Exception {
        //given
        BDDMockito.given(measurementServiceImp.findByIdMeasurement(anyInt()))
                .willThrow(new MeasurementNotFoundException("Measurement not found"));
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurement/1")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Measurement not found")));
    }

    @Test
    public void showRainyDaysTest() throws Exception {
        //given
        int rainyDays = 25;
        BDDMockito.given(measurementServiceImp.getByCountRain()).willReturn(rainyDays);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurement/rainyDaysCount")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(rainyDays)));

        verify(measurementServiceImp, times(1)).getByCountRain();
    }

}
