package com.example.bms;

import java.sql.*;

public class bookData {
    private Integer bookId;
    private String title;
    private String author;
    private String genre;
    private Date date;
    private Double price;
    private String image;

    public bookData(Integer bookId, String title, String author, String genre, Date date, Double price, String image) {
//        this.bookId = bookId.toString();
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.date = date;
        this.price = price;
        this.image = image;
    }

    // getterrrrss
    public Integer getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public Date getDate() {
        return date;
    }
    public Double getPrice() {
        return price;
    }
    public String getImage() {
        return image;
    }
}
