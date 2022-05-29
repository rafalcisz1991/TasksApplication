package com.crud.tasks.task;

import com.crud.tasks.controller.TaskController;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaskMapper taskMapper;

    @MockBean
    DbService dbService;

    @Test
    void shouldGetTasks() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        Task task = new Task(1L, "Test task", "Test description");
        taskList.add(task);
        List<TaskDto> taskDtoList = new ArrayList<>();
        TaskDto taskDto = new TaskDto(1L, "Test TaskDto", "Test description dto");
        taskDtoList.add(taskDto);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskDtoList);
        when(dbService.getAllTasks()).thenReturn(taskList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test TaskDto")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test description dto")));
    }

    @Test
    public void shouldGetASingleTask() throws Exception {
        //Given
        Task task = new Task(1L, "Test task", "Test description");
        TaskDto taskDto = new TaskDto(1L, "Test task DTO", "Description task dto");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.getTaskByID(task.getId())).thenReturn(task);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test task DTO")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Description task dto")));
    }

    @Test
    public void shouldCreateNewTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Dto Task", "Test DTO description");
        Task task = new Task(1L, "Test task", "Test description");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test description")));
    }
}
