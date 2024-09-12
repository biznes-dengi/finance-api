package com.maksyank.finance.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionDepositIntegrationTest {

    @Container
    public static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("container/init.sql");

    @Autowired
    private MockMvc mvc;

    @DynamicPropertySource
    static void registerProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @Nested
    class Error {

        @ParameterizedTest
        @ArgumentsSource(ValidationArguments.class)
        void checkValidationWhileRead(final String month, final String year, final String errorMessage) throws Exception {
            mvc.perform(get("/saving/%s/transaction/deposit/month".formatted("1"))
                            .queryParam("userId", "1")
                            .queryParam("month", month)
                            .queryParam("year", year)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(errorMessage));
        }

        static class ValidationArguments implements ArgumentsProvider {

            @Override
            public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
                return Stream.of(Arguments.of("222222", "2005", "The 'month' field must contain value between [1,12]."),
                        Arguments.of("2", "1913", "The 'year' field must contain value more than [1970]."));
            }
        }
    }
}
