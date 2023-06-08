package com.ase.bookmarkService;

import com.ase.bookmarkService.repository.IBookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookmarkServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmarkServiceApplication.class, args);
	}
}
