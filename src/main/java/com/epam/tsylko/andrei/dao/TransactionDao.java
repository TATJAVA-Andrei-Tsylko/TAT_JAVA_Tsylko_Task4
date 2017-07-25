package com.epam.tsylko.andrei.dao;


import com.epam.tsylko.andrei.dao.exception.DAOException;

import java.sql.Connection;

public interface TransactionDao {
    void makeBookNotFree(int bookId, boolean booksFreeStatus,Connection connection) throws DAOException;
}
