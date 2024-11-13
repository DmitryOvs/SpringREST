package by.chukotka.sensorv2.It;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractRestControllerBaseTest {

    @Container
    static final MySQLContainer MySQL_CONTAINER;

    static {
        MySQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql:latest"))
                .withUsername("root")
                .withPassword("root")
                .withDatabaseName("weather_testcontainers");

        MySQL_CONTAINER.start();
    }


    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MySQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MySQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MySQL_CONTAINER::getPassword);
    }
}
