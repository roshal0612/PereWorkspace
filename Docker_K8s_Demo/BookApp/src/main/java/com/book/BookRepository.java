package com.book;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Serializable>{

}
