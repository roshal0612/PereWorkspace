package com.testk8s;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PostMapping("/book")
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		Book entity = bookService.addBook(book);
		return new ResponseEntity<>(entity, HttpStatus.CREATED);
	}
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks(){
		List<Book> books = bookService.getBooks();
		return new ResponseEntity<>(books, HttpStatus.CREATED);
	}
	
	@GetMapping("/book/{bookId}")
	public ResponseEntity<Book> getBooks(@PathVariable Integer bookId){
		Book book = bookService.getBookById(bookId);
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}
}
