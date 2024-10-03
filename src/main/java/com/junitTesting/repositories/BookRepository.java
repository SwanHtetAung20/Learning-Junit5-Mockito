package com.junitTesting.repositories;

import com.junitTesting.enitites.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
