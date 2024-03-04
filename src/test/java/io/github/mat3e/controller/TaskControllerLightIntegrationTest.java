package io.github.mat3e.controller;

import io.github.mat3e.model.Task;
import io.github.mat3e.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(TaskController.class)
public class TaskControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() {
        //given
        String description = "foo";
        when(repo.findById(anyInt())).thenReturn(Optional.of(new Task(description, LocalDateTime.now())));

        //when+then
        try {
            mockMvc.perform(get("/tasks/123"))
                    .andDo(print())
//                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
                    .andExpect(MockMvcResultMatchers.content().string(containsString(description)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
