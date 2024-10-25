package com.example.bms;

import java.sql.*;

public class customerData {
    private Integer bookId;
    private Integer customerId;
    private String title;
    private String author;
    private String genre;
    private Integer quantity;
    private Double price;
    private Date date;

    public customerData(Integer bookId, Integer customerId, String title, String author, String genre, Integer quantity, Double price, Date date) {
        this.bookId = bookId;
        this.customerId = customerId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public Integer getBookId() {
        return bookId;
    }
    public Integer getCustomerId() {
        return customerId;
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
    public Integer getQuantity() {
        return quantity;
    }
    public Double getPrice() {
        return price;
    }
    public Date getDate() {
        return date;
    }
}
