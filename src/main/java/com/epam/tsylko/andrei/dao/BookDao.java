package com.epam.tsylko.andrei.dao;


import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.dao.exception.DAOException;

import java.sql.Connection;
import java.util.List;

public interface BookDao {

    void addBook(Book book) throws DAOException;

    Book getBook(int bookId) throws DAOException;

    List<Book> getBooks() throws DAOException;

    void editBook(Book book) throws DAOException;

    void makeBookUnAvailable(int bookId, boolean booksAvailabilityStatus) throws DAOException;

    List<Book> sortFreeBooksByDate(String sortOrder) throws DAOException;

    List<Book> sortAllBooksByDate() throws DAOException;

}
