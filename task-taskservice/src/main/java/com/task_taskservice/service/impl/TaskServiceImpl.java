package com.task_taskservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_taskservice.entity.Task;
import com.task_taskservice.exception.CustomException;
import com.task_taskservice.repository.TaskRepository;
import com.task_taskservice.service.TaskService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void createTask(String object, Map<String, Object> result) throws JSONException, JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Task task = objectMapper.readValue(object, Task.class);
        taskRepository.save(task);
        result.put("success", "Task save successfuly");
        result.put("TaskId", task.getTaskId());
    }

    @Override
    public Task getTaskById(long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new CustomException("Task not found with :" + taskId));
        return task;
    }

    @Override
    public void updateTaskByTaskId(String object, Map<String, Object> result) {
    }

    @Override
    public void deleteTaskByTaskId(long taskId, Map<String, String> result) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found with :" + taskId));
        taskRepository.deleteById(task.getTaskId());
        result.put("success", "Deleted Successfully");

    }

    @Override
    public void getTaskByUserId(long userId) {

    }
}
