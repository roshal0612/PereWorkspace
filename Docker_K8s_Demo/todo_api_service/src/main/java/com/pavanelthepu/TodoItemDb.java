package com.pavanelthepu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

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

}
