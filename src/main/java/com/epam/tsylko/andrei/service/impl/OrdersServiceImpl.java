package com.epam.tsylko.andrei.service.impl;

import com.epam.tsylko.andrei.dao.OrdersRepositoryDao;
import com.epam.tsylko.andrei.dao.UserDao;
import com.epam.tsylko.andrei.dao.exception.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.entity.OrdersRepository;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.OrdersService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;


public class OrdersServiceImpl implements OrdersService {
    private final static Logger logger = Logger.getLogger(OrdersServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void reserveBook(Book book, User user) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersServiceImpl.reserveBook()");
        }

        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {

            synchronized (this) {

                    if (logger.isDebugEnabled()) {
                        logger.debug("OrdersServiceImpl.reserveBook() -> synchronized");
                    }

                ordersRepositoryDao.reserveBookByUser(book, user);
            }

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in reserveBook() method in service layer, class OrdersServiceImpl", e);
        }
    }

    @Override
    public List<OrdersRepository> getAllBooksBookedOrTakenByUser(User user) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersServiceImpl.getAllBooksBookedOrTakenByUser()");
        }

        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();
        List<OrdersRepository> ordersRepositoryList;
        try {

            ordersRepositoryList = ordersRepositoryDao.getAllBooksBookedOrTakenByUser(user);

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in getAllBooksBookedOrTakenByUser() method in service layer, class OrdersServiceImpl", e);
        }
        return ordersRepositoryList;
    }

    @Override
    public void cancelBookReservation(OrdersRepository ordersRepository) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersServiceImpl.cancelBookReservation()");
        }

        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {

            ordersRepositoryDao.cancelBookReservation(ordersRepository);

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in cancelBookReservation() method in service layer, class OrdersServiceImpl", e);

        }
    }

    @Override
    public void setBookIsTakenAwayByUser(int ordersRepositoryId) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersServiceImpl.setBookIsTakenAwayByUser()");
        }

        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {

            if (ordersRepositoryId != 0) {
                ordersRepositoryDao.setBookIsTakenAwayByUser(ordersRepositoryId);
            } else {
                throw new ServiceException("Request doesn't content ordersId");
            }

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in setBookIsTakenAwayByUser() method in service layer, class OrdersServiceImpl", e);

        }
    }

    @Override
    public void setBookIsReturnedByUser(OrdersRepository ordersRepository) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("OrdersServiceImpl.setBookIsReturnedByUser()");
        }

        OrdersRepositoryDao ordersRepositoryDao = daoFactory.getMysqlOrdersRepositoryDao();

        try {

            if (ordersRepository.getId() != 0) {
                ordersRepositoryDao.setBookIsReturnedByUser(ordersRepository);
            } else {
                throw new ServiceException("Request doesn't content ordersId");
            }

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in setBookIsReturnedByUser() method in service layer, class OrdersServiceImpl", e);

        }
    }
}
