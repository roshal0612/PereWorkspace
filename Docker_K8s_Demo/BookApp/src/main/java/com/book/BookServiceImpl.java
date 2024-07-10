package com.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookrepo;
	
	public Book addBook(Book book) {
		return bookrepo.save(book);
	}

	public List<Book> getBooks() {
		return bookrepo.findAll();
	}
	
	public Book getBookById(Integer bookId) {
		return bookrepo.findById(bookId).get();
	}
	
}
