package com.pavanelthepu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

	@Autowired
	private TodoItemDb todoDb;
	
	public List<TodoItem> findAll() {
        return todoDb.findAll();
    }

	public TodoItem save(TodoItem item) {
        return todoDb.save(item);
    }
	
}
