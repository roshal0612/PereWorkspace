package com.pavanelthepu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.pavanelthepu.data.TodoRepository;
import com.pavanelthepu.entity.TodoItem;

@Component
public class TodoItemDb implements TodoRepository {

	private final List<TodoItem> todoList = new ArrayList<>();

	private final AtomicInteger counter = new AtomicInteger();

	@Override
	public List<TodoItem> findAll() {
		return todoList;
	}

	@Override
	public TodoItem save(TodoItem item) {
		item.setId(counter.incrementAndGet());
		todoList.add(item);
		return item;
	}

	@Override
	public long countByCompleted(boolean status) {
		long count = todoList.stream()
			.filter(todo -> todo.isStatus())
			.count();
		return count;
	}
	
}
