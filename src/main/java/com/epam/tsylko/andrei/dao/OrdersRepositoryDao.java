package com.epam.tsylko.andrei.dao;

import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.entity.OrdersRepository;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.dao.exception.DAOException;

import java.util.List;

public interface OrdersRepositoryDao {

    void reserveBookByUser(Book book, User user)throws DAOException;

    List<OrdersRepository> getAllBooksBookedOrTakenByUser(User user)throws DAOException;

    void cancelBookReservation(OrdersRepository ordersRepository)throws DAOException;

    void setBookIsTakenAwayByUser(int ordersRepositoryId)throws DAOException;

    void setBookIsReturnedByUser(OrdersRepository ordersRepository)throws DAOException;
}
