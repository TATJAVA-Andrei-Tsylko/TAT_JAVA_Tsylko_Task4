package com.epam.tsylko.andrei.dao.impl;

import com.epam.tsylko.andrei.dao.AddressDao;
import com.epam.tsylko.andrei.dao.pool.ConnectionPool;
import com.epam.tsylko.andrei.entity.Address;
import com.epam.tsylko.andrei.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDaoImpl implements AddressDao {
    private final static Logger logger = Logger.getLogger(AddressDaoImpl.class);
    private static final String ADD_ADDRESS = "INSERT INTO `library`.`address` (`country`, `city`, `street`, `house`, `room`) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_ADDRESS = "UPDATE `library`.`address` SET `country`=?, `city`=?, `street`=?, `house`=?, `room`=? WHERE `id`=?;";
    private static final String GET_ADDRESS = "SELECT `id`, `country`, `city`, `street`, `house`, `room` FROM library.address where id = ?;";

    @Override
    public void addAddressToUser(Address address) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("AddressDaoImpl.addAddressToUser()");
        }

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(ADD_ADDRESS);
            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getStreet());
            ps.setInt(4, address.getHouse());
            ps.setInt(5, address.getRoom());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot add user's address to DB", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public void updateUsersAddress(Address address) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("AddressDaoImpl.updateUsersAddress()");
        }

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {

            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(UPDATE_ADDRESS);
            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getStreet());
            ps.setInt(4, address.getHouse());
            ps.setInt(5, address.getRoom());
            ps.setInt(6, address.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot update user's address", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps);
            }
        }
    }

    @Override
    public Address getAddress(int addressId) throws DAOException {

        if (logger.isDebugEnabled()) {
            logger.debug("AddressDaoImpl.getAddress()");
        }

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Address> addresses = new ArrayList<>();

        try {

            connectionPool = connectionPool.getInstance();
            connection = connectionPool.getConnection();

            ps = connection.prepareStatement(GET_ADDRESS);
            ps.setInt(1, addressId);
            resultSet = ps.executeQuery();
            resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                throw new DAOException("Nothing receive from db");
            }


            do {
                Address address = new Address();
                address.setId(resultSet.getInt(1));
                address.setCountry(resultSet.getString(2));
                address.setCity(resultSet.getString(3));
                address.setStreet(resultSet.getString(4));
                address.setHouse(resultSet.getInt(5));
                address.setRoom(resultSet.getInt(6));
                addresses.add(address);
            } while (resultSet.next());

        } catch (SQLException e) {
            throw new DAOException("Operation failed in database. Cannot retrieve address", e);
        } finally {
            if (connectionPool != null) {
                connectionPool.closeConnection(connection, ps, resultSet);
            }
        }

        return addresses.get(0);
    }
}
