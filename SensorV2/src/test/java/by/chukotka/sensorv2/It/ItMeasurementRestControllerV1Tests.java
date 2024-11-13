package by.chukotka.sensorv2.It;

import com.fasterxml.jackson.databind.ObjectMapper;
import by.chukotka.sensorv2.DTO.MeasurementDTO;
import by.chukotka.sensorv2.model.Measurement;
import by.chukotka.sensorv2.repositories.MeasurementRepository;
import by.chukotka.sensorv2.util.DataUtilsMeasurement;
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

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
//@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItMeasurementRestControllerV1Tests //extends AbstractRestControllerBaseTest
 {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MeasurementRepository repository;

    @BeforeEach
    public void setUp() {repository.deleteAll();
    }


    @Test
    @DisplayName("Test create measurement functionality")
    public void givenMeasurementDto_whenCreateDeveloper_thenSuccessResponse() throws Exception {
        //given
        MeasurementDTO dto = DataUtilsMeasurement.getOneDTOTransient();
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature", CoreMatchers.is(-25.0)));
             //   .andExpect(MockMvcResultMatchers.jsonPath("$.senS", CoreMatchers.notNullValue()));
    }

    @Test
    @DisplayName("Test get measurement by id functionality")
    public void givenId_whenGetById_thenSuccessResponse() throws Exception {
        //given
        Measurement measurement = DataUtilsMeasurement.getTwoTransient();
        repository.save(measurement);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurement/" + measurement.getIdMeasurement())
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
        Measurement measurement1 = DataUtilsMeasurement.getOneTransient();
        repository.save(measurement1);
        Measurement measurement2 = DataUtilsMeasurement.getTwoTransient();
        repository.save(measurement2);
        Measurement measurement3 = DataUtilsMeasurement.getThreeTransient();
        repository.save(measurement3);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/measurement/rainyDaysCount")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(1)));

    }

}







