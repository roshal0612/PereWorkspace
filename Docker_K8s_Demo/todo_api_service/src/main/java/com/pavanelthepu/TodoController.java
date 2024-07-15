package com.pavanelthepu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoItem> getAllTodos() {
        return todoService.findAll();
    }

    @PostMapping
    public TodoItem addTodoItem(@RequestBody TodoItem item) {
        return todoService.save(item);
    }
}
