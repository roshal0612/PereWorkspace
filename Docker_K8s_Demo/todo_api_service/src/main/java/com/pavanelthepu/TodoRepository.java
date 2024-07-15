package com.pavanelthepu;

import java.util.List;

public interface TodoRepository {

	List<TodoItem> findAll();
	
    TodoItem save(TodoItem item);
}
