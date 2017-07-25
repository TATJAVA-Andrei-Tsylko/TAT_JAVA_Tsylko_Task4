package com.epam.tsylko.andrei.service;


import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.entity.OrdersRepository;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.service.exception.ServiceException;

import java.util.List;

public interface OrdersService {
    void reserveBook(Book book, User user)throws ServiceException;

    List<OrdersRepository> getAllBooksBookedOrTakenByUser(User user)throws ServiceException;

    void cancelBookReservation(OrdersRepository ordersRepository)throws ServiceException;

    void setBookIsTakenAwayByUser(int ordersRepositoryId)throws ServiceException;

    void setBookIsReturnedByUser(OrdersRepository ordersRepository)throws ServiceException;
}
