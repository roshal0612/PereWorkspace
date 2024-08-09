package com.pavanelthepu.data;

import java.util.List;

import com.pavanelthepu.entity.TodoItem;

public interface TodoRepository {

	List<TodoItem> findAll();
	
    TodoItem save(TodoItem item);

	long countByCompleted(boolean status);
}
