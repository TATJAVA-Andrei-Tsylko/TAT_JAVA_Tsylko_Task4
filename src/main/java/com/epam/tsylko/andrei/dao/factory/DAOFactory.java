package com.epam.tsylko.andrei.dao.factory;

import com.epam.tsylko.andrei.dao.TransactionDao;
import com.epam.tsylko.andrei.dao.BookDao;
import com.epam.tsylko.andrei.dao.OrdersRepositoryDao;
import com.epam.tsylko.andrei.dao.UserDao;
import com.epam.tsylko.andrei.dao.AddressDao;
import com.epam.tsylko.andrei.dao.impl.AddressDaoImpl;
import com.epam.tsylko.andrei.dao.impl.BookDaoImpl;
import com.epam.tsylko.andrei.dao.impl.OrdersRepositoryDaoImpl;
import com.epam.tsylko.andrei.dao.impl.TransactionDaoImpl;
import com.epam.tsylko.andrei.dao.impl.UserDaoImpl;

public final class DAOFactory {

    private final BookDao mysqlBookImpl = new BookDaoImpl();
    private final TransactionDao mysqlTransactionImpl = new TransactionDaoImpl();
    private final UserDao mysqlUserImpl = new UserDaoImpl();
    private final AddressDao mysqlAddressDao = new AddressDaoImpl();
    private final OrdersRepositoryDao mysqlOrdersRepositoryDao = new OrdersRepositoryDaoImpl();

    private DAOFactory(){}

    public static class SingletonHolderForDao{
        public static final DAOFactory instance = new DAOFactory();
    }

    public static DAOFactory getInstance(){
        return SingletonHolderForDao.instance;
    }

    public BookDao getMysqlBookImpl() {
        return mysqlBookImpl;
    }

    public UserDao getMysqlUserImpl() {
        return mysqlUserImpl;
    }

    public AddressDao getMysqlAddressDao() {
        return mysqlAddressDao;
    }

    public TransactionDao getMysqlTransactionImpl() {
        return mysqlTransactionImpl;
    }

    public OrdersRepositoryDao getMysqlOrdersRepositoryDao() {
        return mysqlOrdersRepositoryDao;
    }
}
