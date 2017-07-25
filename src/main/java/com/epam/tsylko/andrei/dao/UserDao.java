package com.epam.tsylko.andrei.dao;

import com.epam.tsylko.andrei.entity.Role;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.dao.exception.DAOException;

import java.util.List;

public interface UserDao {

    void registration(User user)throws DAOException;

    void editUser(User user)throws DAOException;

    User getUser(int userId)throws DAOException;

    User getUser(User user)throws DAOException;

    List<User> getAllUsers()throws DAOException;

    void changeUserBlockStatus(int userId,boolean blockStatus)throws DAOException;

    void changeRole(int userId, Role role)throws DAOException;


}
