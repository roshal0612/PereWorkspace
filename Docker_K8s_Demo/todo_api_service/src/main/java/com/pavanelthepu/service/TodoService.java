package com.pavanelthepu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pavanelthepu.data.TodoRepository;
import com.pavanelthepu.entity.TodoItem;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepo;
	
	private Counter todoCounter;
	
	private MeterRegistry meterRegistry;
	
	public TodoService(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		this.todoCounter = Counter.builder("todo.created.counter")
				.tags("status", "created")
				.description("Total no. of todo items created")
				.register(meterRegistry);
	}
	
	public List<TodoItem> findAll() {
        return todoRepo.findAll();
    }

	public TodoItem save(TodoItem item) {
		
		TodoItem save = todoRepo.save(item);
		this.todoCounter.increment();
        return save;
    }
	
	private void recordPendingItems() {
		long pendingItems = todoRepo.countByCompleted(false);
		Tags tags = Tags.of("status", "pending");
		meterRegistry.gauge("todo.pending.count", tags, pendingItems);
	
	}
	
}
