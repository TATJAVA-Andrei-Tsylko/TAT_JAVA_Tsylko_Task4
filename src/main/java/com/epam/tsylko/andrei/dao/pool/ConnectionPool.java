package com.epam.tsylko.andrei.dao.pool;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
//TODO import
import java.sql.*;

public class ConnectionPool {
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
//    private ConnectionPool pool = new ConnectionPool();
    private Connection instance;
    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    //TODO private constructor
    private ConnectionPool() throws ConnectionPoolException {
        try {
            DBResourceManager dbResourceManager = DBResourceManager.getInstance();
            logger.info("System found database property file");
            this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
            this.user = dbResourceManager.getValue(DBParameter.DB_USER);
            this.url = dbResourceManager.getValue(DBParameter.DB_URL);
            this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
            this.instance = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Cannot get connection from pool", e);
        }


//            throw new ConnectionPoolException("Cannot create connection pool", e);

    }

    //TODO static method nd return

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        return new ConnectionPool();
    }

    public Connection getConnection() throws ConnectionPoolException, SQLException {

//        try{
//            instance = DriverManager.getConnection(url, user, password);
//
//            if(logger.isInfoEnabled()){
//                logger.info("Get connection from database");
//            }
//
//        }catch (SQLException e){
//            throw new ConnectionPoolException("Cannot get connection from pool", e);
//        }
        if(logger.isDebugEnabled()){
            logger.debug("Connection hash number: " + instance.getMetaData().hashCode());
        }

        return instance;
    }

    public void returnConnection(Connection connection) throws ConnectionPoolException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ConnectionPoolException("Cannot return connection to pool.", e);
        }
    }

    public void closeConnection(Connection connection, Statement st, ResultSet rs) {


        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "ResultSet isn't return to the pool");
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Statement isn't return to the pool");
            }
        }
        try {
//            TODO
            this.returnConnection(connection);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool");
        }

    }

    public void closeConnection(Connection connection, Statement st) {

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Statement isn't return to the pool");
            }
        }
        try {
//            TODO
            this.returnConnection(connection);
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool");
        }
    }
}
