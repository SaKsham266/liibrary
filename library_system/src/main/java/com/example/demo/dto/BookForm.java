package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookForm {

    @NotBlank(message = "Title is required.")
    @Size(max = 150, message = "Title must be at most 150 characters.")
    private String title;

    @NotBlank(message = "Author is required.")
    @Size(max = 120, message = "Author must be at most 120 characters.")
    private String author;

    @NotBlank(message = "Publisher is required.")
    @Size(max = 120, message = "Publisher must be at most 120 characters.")
    private String publisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
