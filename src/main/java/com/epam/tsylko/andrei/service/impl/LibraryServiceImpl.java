package com.epam.tsylko.andrei.service.impl;

import com.epam.tsylko.andrei.dao.BookDao;
import com.epam.tsylko.andrei.dao.exception.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.util.Util;
import com.epam.tsylko.andrei.service.util.exception.UtilException;
import org.apache.log4j.Logger;


import java.util.List;


public class LibraryServiceImpl implements LibraryService {
    private final static Logger logger = Logger.getLogger(ResidenceServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void addNewBook(Book book) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("LibraryServiceImpl.addNewBook()");
        }

        BookDao bookDao = daoFactory.getMysqlBookImpl();
        try {

            if (checkInputtedBookData(book)) {

                synchronized (this) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("LibraryServiceImpl.addNewBook() -> synchronized");
                    }
                    bookDao.addBook(book);
                }

            }

        } catch (UtilException e) {
            throw new ServiceException("Incorrect values. This values don't math to book object", e);
        } catch (DAOException e) {
            throw new ServiceException("Error occurred in addNewBook() method", e);
        }
    }

    @Override
    public void addEditedBook(Book book) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("LibraryServiceImpl.addEditedBook()");
        }

        BookDao bookDao = daoFactory.getMysqlBookImpl();

        try {

            if (checkInputtedBookData(book)) {

                synchronized (this) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("LibraryServiceImpl.addEditedBook() -> synchronized");
                    }
                    bookDao.editBook(book);
                }
            }

        } catch (UtilException e) {
            throw new ServiceException("Incorrect values. This values don't math to book object", e);
        } catch (DAOException e) {
            throw new ServiceException("Error occurred in addEditedBook() method in service layer", e);
        }
    }

    @Override
    public List<Book> retrieveAllBooksFromLibrary() throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("LibraryServiceImpl.retrieveAllBooksFromLibrary()");
        }

        BookDao bookDao = daoFactory.getMysqlBookImpl();
        List<Book> books;

        try {

            books = bookDao.getBooks();


        } catch (DAOException e) {
            throw new ServiceException("Error occurred in retrieveAllBooksFromLibrary() method in service layer", e);
        }
        return books;
    }

    @Override
    public Book getBookFromTheLibrary(int bookId) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("LibraryServiceImpl.getBookFromTheLibrary()");
        }

        BookDao bookDao = daoFactory.getMysqlBookImpl();
        Book book;

        try {

            book = bookDao.getBook(bookId);

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in getBookFromTheLibrary() method in service layer", e);

        }
        return book;
    }


    @Override
    public List<Book> sortFreeBooksByDate(String order) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("LibraryServiceImpl.sortFreeBooksByDate()");
        }

        BookDao bookDao = daoFactory.getMysqlBookImpl();
        List<Book> books;

        try {

            books = bookDao.sortFreeBooksByDate(order);

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in sortFreeBooksByDate() method in service layer", e);
        }
        return books;
    }

    @Override
    public List<Book> sortAllBooksByDate() throws ServiceException {
        return null;
    }

    @Override
    public void makeBookUnAvailable(int bookId, boolean booksAvailabilityStatus) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("LibraryServiceImpl.makeBookUnAvailable()");
        }

        BookDao bookDao = daoFactory.getMysqlBookImpl();

        try {

            bookDao.makeBookUnAvailable(bookId, booksAvailabilityStatus);

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in makeBookUnAvailable() method in service layer", e);
        }
    }

    private boolean checkInputtedBookData(Book book) throws UtilException {

        Util.isNull(book.getBooksName(), book.getAuthorSurname(), book.getIsbn());
        Util.isEmptyString(book.getBooksName(), book.getAuthorSurname());
        Util.isNumberPositive(book.getIsbn(), book.getPaperBack(), book.getPrintRun());
        Util.checkISBN(book.getIsbn());

        return true;
    }

}
