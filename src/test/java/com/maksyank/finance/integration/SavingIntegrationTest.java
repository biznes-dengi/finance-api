package com.maksyank.finance.integration;

import com.maksyank.finance.saving.domain.enums.SavingState;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SavingIntegrationTest {

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
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class Successful {

        @Test
        @Order(1)
        void getSavingByUserIdAndState() throws Exception {
            mvc.perform(get("/saving")
                            .queryParam("state", SavingState.ACTIVE.name())
                            .queryParam("userId", "1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpectAll(getSavingJsonMatcher(true));
        }

        @Test
        @Order(2)
        void getSavingById() throws Exception {
            mvc.perform(get("/saving/1")
                            .queryParam("userId", "1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpectAll(getSavingJsonMatcher(false));
        }

        @Test
        @Order(3)
        void createSaving() throws Exception {
            mvc.perform(post("/saving")
                            .queryParam("userId", "1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getSavingCreationBody()))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        @Order(4)
        void updateSaving() throws Exception {
            mvc.perform(put("/saving/2")
                            .queryParam("userId", "1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(getSavingUpdateBody()))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        @Order(5)
        void deleteSaving() throws Exception {
            mvc.perform(delete("/saving/2")
                            .queryParam("userId", "1"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful());
        }

        private ResultMatcher[] getSavingJsonMatcher(final boolean isList) {
            return new ResultMatcher[]{
                    jsonPath("$%s.id".formatted(isList ? "[0]" : EMPTY)).value(1),
                    jsonPath("$%s.title".formatted(isList ? "[0]" : EMPTY)).value("Trichilia"),
                    jsonPath("$%s.amount".formatted(isList ? "[0]" : EMPTY)).value(950),
                    jsonPath("$%s.targetAmount".formatted(isList ? "[0]" : EMPTY)).value(1500.50),
                    jsonPath("$%s.image".formatted(isList ? "[0]" : EMPTY)).isEmpty()
            };
        }

        private String getSavingCreationBody() {
            return """
                    {
                       "id":2,
                       "title":"TEST_TITLE",
                       "state":"ACHIEVED",
                       "currency": "EUR",
                       "description":"TEST_DESCRIPTION",
                       "amount":228.00,
                       "targetAmount":1408.00,
                       "deadline":"2050-10-24"
                    }""";
        }

        private String getSavingUpdateBody() {
            return """
                    {
                       "title":"UPDATED_TITLE",
                       "currency": "RUB",
                       "description":"UPDATED_DESCRIPTION",
                       "targetAmount":1408.00,
                       "deadline":"2055-10-24"
                    }""";
        }
    }

    @Nested
    class Error {

        @Test
        void checkNotExistingUser() throws Exception {
            // GIVEN
            final var userId = "999999";
            // WHEN
            mvc.perform(get("/saving")
                            .queryParam("state", SavingState.ACTIVE.name())
                            .queryParam("userId", userId)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Entity 'User' not found by attribute 'id' = %s".formatted(userId)));
        }

        @Test
        void checkValidationWhileUpdate() throws Exception {
            mvc.perform(put("/saving/1")
                            .queryParam("userId", "1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "title":"UPDATED_TITLE"
                                    }"""))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("must not be null"));
        }
    }
}
