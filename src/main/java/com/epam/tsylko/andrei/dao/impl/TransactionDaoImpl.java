package com.epam.tsylko.andrei.dao.impl;



import com.epam.tsylko.andrei.dao.TransactionDao;
import com.epam.tsylko.andrei.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDaoImpl implements TransactionDao {
    private final static Logger logger = Logger.getLogger(TransactionDaoImpl.class);
    private static final String FREE_BOOK = "UPDATE `library`.`book` SET `reservation`=? WHERE `id`=?;";

    @Override
    public void makeBookNotFree(int bookId, boolean booksFreeStatus, Connection connection) throws DAOException {

            if (logger.isDebugEnabled()) {
                logger.debug("BookDaoImpl.makeBookNotFree()");
            }

            PreparedStatement preparedStatement = null;
            try {

                preparedStatement = connection.prepareStatement(FREE_BOOK);
                preparedStatement.setBoolean(1, booksFreeStatus);
                preparedStatement.setInt(2, bookId);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException("Cannot change reservation status", e);
            }
        }

}
