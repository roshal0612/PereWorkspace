package com.book;

import java.util.List;

public interface BookService {

	public Book addBook(Book book);
	
	public List<Book> getBooks();
	
	public Book getBookById(Integer bookId);
}
