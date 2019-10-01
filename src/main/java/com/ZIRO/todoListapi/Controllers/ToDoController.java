package com.ZIRO.todoListapi.Controllers;

import com.ZIRO.todoListapi.Entities.ToDo;
import com.ZIRO.todoListapi.Repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/todo")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    private HttpStatus httpStatus;
    
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResponseEntity<List<ToDo>> getAllToDoList(@RequestBody Map<String, String> user){
        List<ToDo> results = new ArrayList<>();
        try{
            long userId = Long.parseLong(user.get("user_id"));
            results = toDoRepository.findAllByCreatedByUserId(userId);
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in listing all ToDo list, please see below details");
            System.out.println(E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(results, httpStatus);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<ToDo> getOne(@PathVariable("id") long id){
        ToDo todo = new ToDo();
        try{
            todo = toDoRepository.findById(id).get();
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in listing todo list item, please see below details");
            System.out.println(E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(todo, httpStatus);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addToDo(@RequestBody ToDo todo){
        Map<String, Object> results = new HashMap<>();
        try{
            ToDo saved = toDoRepository.save(todo);
            results.put("data", saved);
            results.put("Success", "Yes");
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in adding todo item, please see below details");
            results.put("Success", "No");
			results.put("error", E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(results, httpStatus);
    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addMultipleToDo(@RequestBody List<ToDo> todoList){
        Map<String, Object> results = new HashMap<>();
        try{
            List<ToDo> saved = toDoRepository.saveAll(todoList);
            results.put("data", saved);
            results.put("Success", "Yes");
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in adding all selected ToDo list, please see below details");
            results.put("Success", "No");
			results.put("error", E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(results, httpStatus);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateToDo(@RequestBody ToDo todo){
        Map<String, Object> results = new HashMap<>();
        try{
            ToDo saved = toDoRepository.save(todo);
            results.put("data", saved);
            results.put("Success", "Yes");
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in updating todo item, please see below details");
            results.put("Success", "No");
			results.put("error", E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(results, httpStatus);
    }

    @RequestMapping(value = "/updateAll", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> updateMultipleToDo(@RequestBody List<ToDo> todoList){
        Map<String, Object> results = new HashMap<>();
        try{
            List<ToDo> saved = toDoRepository.saveAll(todoList);
            results.put("data", saved);
            results.put("Success", "Yes");
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in updating all selected ToDo list, please see below details");
            results.put("Success", "No");
			results.put("error", E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(results, httpStatus);
    }


    @RequestMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteToDo (@PathVariable("id") long id){
        Map<String, Object> results = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try{
            toDoRepository.deleteById(id);
            results.put("Success", "Yes");
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in deleting todo item, please see below details");
            results.put("Success", "No");
			results.put("error", E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(results, httpStatus);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteMultipleToDo (@RequestBody List<ToDo> todoList){
        Map<String, Object> results = new HashMap<>();
        try{
            toDoRepository.deleteAll(todoList);
            results.put("Success", "Yes");
            httpStatus = HttpStatus.OK;
        }catch(Exception E){
            System.out.println("Error in deleting all selected todo list, please see below details");
            results.put("Success", "No");
			results.put("error", E.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(results, httpStatus);
    }
}
