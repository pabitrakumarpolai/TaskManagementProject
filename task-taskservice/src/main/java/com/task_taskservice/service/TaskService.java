package com.task_taskservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.task_taskservice.entity.Task;
import org.json.JSONException;

import java.util.Map;

public interface TaskService {
     void createTask(String object, Map<String ,Object> result) throws JsonProcessingException, JSONException;
     Task getTaskById(long id);
     void updateTaskByTaskId(String object,Map<String , Object> result);
     void deleteTaskByTaskId(long taskId,Map<String ,String>result );
     void getTaskByUserId(long userId);
}
