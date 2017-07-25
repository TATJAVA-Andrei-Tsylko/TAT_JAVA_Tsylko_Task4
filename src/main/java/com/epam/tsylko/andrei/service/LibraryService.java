package com.epam.tsylko.andrei.service;


import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.service.exception.ServiceException;

import java.util.List;

public interface LibraryService {
    void addNewBook(Book book) throws ServiceException;

    void addEditedBook(Book book) throws ServiceException;

    List<Book> retrieveAllBooksFromLibrary() throws ServiceException;

    Book getBookFromTheLibrary(int bookId) throws ServiceException;

    List<Book> sortFreeBooksByDate(String order) throws ServiceException;

    List<Book> sortAllBooksByDate() throws ServiceException;

    void makeBookUnAvailable(int bookId, boolean booksAvailabilityStatus) throws ServiceException;
}
