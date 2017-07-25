package com.epam.tsylko.andrei.dao.pool;


import com.epam.tsylko.andrei.dao.exception.DAOException;

public class ConnectionPoolException extends DAOException {

    private static final long serialVersionUID = 4777792308554908643L;

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
