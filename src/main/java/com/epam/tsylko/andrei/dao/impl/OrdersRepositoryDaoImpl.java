package com.epam.tsylko.andrei.dao.impl;



import com.epam.tsylko.andrei.dao.OrdersRepositoryDao;
import com.epam.tsylko.andrei.dao.TransactionDao;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.dao.pool.ConnectionPool;
import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.entity.OrdersRepository;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepositoryDaoImpl implements OrdersRepositoryDao {

    private final static Logger logger = Logger.getLogger(OrdersRepositoryDao.class);
    private static final String RESERVED_BOOK = "INSERT INTO `library`.`oredersrepository` (`bookId`, `userId`) VALUES (?,?);";
    private static final String ALL_USERS_BOOKS = "SELECT `id`, `bookId`, `userId`,`dateOfIssuance`,`dateOfTheReturn`,`booking`,`borrowingBook`,returningBook` FROM library.oredersrepository where userId = ?;";
    private static final String REPEALED_BOOK = "UPDATE `library`.`oredersrepository` SET `isBooked`='0' WHERE `id`=?;";
    private static final String USER_TOOK_BOOK = "UPDATE `library`.`oredersrepository` SET `isTakenAway`='1' WHERE `id`=?;";
    private static final String USER_RETURNED_BOOK = "UPDATE `library`.`oredersrepository` SET `isReturned`='1' WHERE `id` = ?;";

    @Override
    public void reserveBookByUser(Book book, User user) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersRepositoryDaoImpl.reserveBookByUser()");
        }


        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {

            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);

            ps = connection.prepareStatement(RESERVED_BOOK);
            ps.setInt(1, book.getId());
            ps.setInt(2, user.getId());

            ps.executeUpdate();
            TransactionDao transactionDao = DAOFactory.getInstance().getMysqlTransactionImpl();
            transactionDao.makeBookNotFree(book.getId(), false, connection);

            connection.commit();


        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.fatal("Exception during rollback operation", e);
            }
            throw new DAOException("Cannot add reserved book to DB", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public List<OrdersRepository> getAllBooksBookedOrTakenByUser(User user) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersRepositoryDaoImpl.getAllBooksBookedOrTakenByUser()");
        }

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<OrdersRepository> ordersRepositories = new ArrayList<>();

        try {

            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(ALL_USERS_BOOKS);
            ps.setInt(1, user.getId());
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                OrdersRepository ordersRepository = new OrdersRepository();
                ordersRepository.setId(resultSet.getInt(1));
                ordersRepository.setBook(new Book(resultSet.getInt(2)));
                ordersRepository.setUser(new User(resultSet.getInt(3)));
                ordersRepository.setDateOfIssuance(resultSet.getDate(4));
                ordersRepository.setDateOfTheReturn(resultSet.getDate(5));
                ordersRepository.setBooking(resultSet.getBoolean(6));
                ordersRepository.setBorrowingBook(resultSet.getBoolean(7));
                ordersRepository.setReturningBook(resultSet.getBoolean(8));
                ordersRepositories.add(ordersRepository);
            }
        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve information from OrdersRepository", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }
        return ordersRepositories;

    }

    @Override
    public void cancelBookReservation(OrdersRepository ordersRepository) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersRepositoryDaoImpl.cancelBookReservation()");
        }

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {

            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);

            ps = connection.prepareStatement(REPEALED_BOOK);
            ps.setInt(1, ordersRepository.getId());
            ps.executeUpdate();

            TransactionDao transactionDao = DAOFactory.getInstance().getMysqlTransactionImpl();
            transactionDao.makeBookNotFree(ordersRepository.getBook().getId(), true, connection);

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
//                TODO throw exception
                logger.fatal("Exception during rollback operation", e);
            }
            throw new DAOException("Book's reservation wasn't cancel", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public void setBookIsTakenAwayByUser(int ordersRepositoryId) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersRepositoryDaoImpl.setBookIsTakenAwayByUser()");
        }

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {

            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(USER_TOOK_BOOK);
            ps.setInt(1, ordersRepositoryId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Cannot change isBorrowingBook status", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public void setBookIsReturnedByUser(OrdersRepository ordersRepository) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersRepositoryDaoImpl.setBookIsReturnedByUser()");
        }

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {

            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();

            connection.setAutoCommit(false);

            ps = connection.prepareStatement(USER_RETURNED_BOOK);
            ps.setInt(1, ordersRepository.getId());
            ps.executeUpdate();

            TransactionDao transactionDao = DAOFactory.getInstance().getMysqlTransactionImpl();
            transactionDao.makeBookNotFree(ordersRepository.getBook().getId(), true, connection);

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.fatal("Exception during rollback operation", e);
            }
            throw new DAOException("Cannot change isReturningBook status", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }
}
