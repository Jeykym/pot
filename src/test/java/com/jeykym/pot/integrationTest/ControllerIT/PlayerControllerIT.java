package com.jeykym.pot.integrationTest.ControllerIT;

import com.jeykym.pot.dto.CreatePlayerRequest;
import com.jeykym.pot.dto.PlayerDTO;
import com.jeykym.pot.integrationTest.AbstractContainerIT;
import com.jeykym.pot.repository.PlayerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class PlayerControllerIT extends AbstractContainerIT {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        playerRepository.deleteAll();
    }

    @Test
    void createPlayer_returns201WithLocationAndBody() throws Exception {
        var result = mockMvc.perform(post("/players")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreatePlayerRequest("Alice"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andReturn();

        var dto = objectMapper.readValue(result.getResponse().getContentAsString(), PlayerDTO.class);
        assertThat(result.getResponse().getHeader("Location"))
                .isEqualTo("/players/" + dto.id());
    }

    @Test
    void createPlayer_returns400OnDuplicateName() throws Exception {
        var body = objectMapper.writeValueAsString(new CreatePlayerRequest("Alice"));
        mockMvc.perform(post("/players").contentType(APPLICATION_JSON).content(body));

        mockMvc.perform((post("/players")).contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("PLAYER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void createPlayer_returns400OnBlankName() throws Exception {
        mockMvc.perform(post("/players")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreatePlayerRequest("   "))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void createPlayer_returns400OnMissingBody() throws Exception {
        mockMvc.perform(post("/players").contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MALFORMED_REQUEST"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }
}


