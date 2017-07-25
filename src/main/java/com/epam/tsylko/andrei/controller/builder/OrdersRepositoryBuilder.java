package com.epam.tsylko.andrei.controller.builder;

import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.entity.OrdersRepository;
import com.epam.tsylko.andrei.entity.User;

import java.util.Map;




public class OrdersRepositoryBuilder implements Builder<OrdersRepository> {
    private final static String ORDER_ID = "orderId";
    private final static String BOOK_ID = "bookId";
    private final static String USER_ID = "userId";

    @Override
    public OrdersRepository build(Map<String, String> dataFromRequest) {
        OrdersRepository ordersRepository = new OrdersRepository(ControllerUtil.parseStringToIntFromMap(dataFromRequest, ORDER_ID),
        new Book(ControllerUtil.parseStringToIntFromMap(dataFromRequest, BOOK_ID)),
        new User(ControllerUtil.parseStringToIntFromMap(dataFromRequest, USER_ID)));
        return ordersRepository;
    }
}
