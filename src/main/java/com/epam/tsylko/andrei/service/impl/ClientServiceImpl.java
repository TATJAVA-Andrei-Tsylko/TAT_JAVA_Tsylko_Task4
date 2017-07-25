package com.epam.tsylko.andrei.service.impl;


import com.epam.tsylko.andrei.dao.UserDao;
import com.epam.tsylko.andrei.dao.exception.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entity.Role;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.util.Util;
import com.epam.tsylko.andrei.service.util.exception.UtilException;
import org.apache.log4j.Logger;

import java.util.List;


public class ClientServiceImpl implements ClientService {

    private final static Logger logger = Logger.getLogger(ClientServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private final static boolean USER_DISABLED = false;

    @Override
    public void registration(User user) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.registration()");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {
            checkRegistrationParams(user);
            Util.checkEmail(user);
            user.setPassword(Util.getHashForPassword(user.getPassword()));

            if (logger.isDebugEnabled()) {
                logger.debug("registrtion user " + user.toString());
            }

            synchronized (this) {

                if (logger.isDebugEnabled()) {
                    logger.debug("ClientServiceImpl.registration() -> synchronized");
                }

                checkLogin(user);
                userDao.registration(user);
            }

        } catch (DAOException e) {
            throw new ServiceException("Service layer. exception occurred in dao layer", e);
        } catch (UtilException e) {
            throw new ServiceException("Check inputed params in registration form", e);
        }
    }

    @Override
    public void signIn(User user) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.signIn()");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {
            User userFromDB = userDao.getUser(user);

            if (logger.isDebugEnabled()) {
                logger.debug("User from DB " + userFromDB.toString());
            }

            Util.checkHash(userFromDB.getPassword(), user.getPassword());
            if (userFromDB.isStatus() == USER_DISABLED) {
                throw new ServiceException("User was banned.");
            }
        } catch (DAOException e) {
            throw new ServiceException("Service layer. exception occurred in dao layer", e);
        } catch (UtilException e) {
            throw new ServiceException("Incorrect password " + user.getPassword());
        }
    }

    @Override
    public void editUser(User user) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.editUser()");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {

            Util.checkEmail(user);
            if (user.getPassword() != null) {
                user.setPassword(Util.getHashForPassword(user.getPassword()));
            } else {
                user.setPassword(userDao.getUser(user.getId()).getPassword());
            }

            synchronized (this) {

                if (logger.isDebugEnabled()) {
                    logger.debug("ClientServiceImpl.editUser() -> synchronized");
                }

                userDao.editUser(user);
            }

        } catch (DAOException e) {
            throw new ServiceException("Service layer. exception occurred in dao layer", e);
        } catch (UtilException e) {
            throw new ServiceException("Error during editUser procedure", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.getAllUsers()");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();
        List<User> users;

        try {

            users = userDao.getAllUsers();

        } catch (DAOException e) {
            throw new ServiceException("Error in service layer. Method getAllUsers() tried to get list from dao layer", e);
        }
        return users;
    }

    @Override
    public User getUser(int userId) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.getUser()");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user;

        try {

            user = userDao.getUser(userId);

        } catch (DAOException e) {
            throw new ServiceException("Error in service layer. Method getUser() tried to get a user from dao layer", e);
        }
        return user;
    }

    @Override
    public User getUser(User user) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.getUser(User user)");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();
        User userFromRequest;
        try {

            userFromRequest = userDao.getUser(user);

        } catch (DAOException e) {
            throw new ServiceException("Error in service layer. Method getUser() tried to get a user from dao layer", e);

        }

        return userFromRequest;
    }

    @Override
    public boolean checkUserRole(int userId, Role superAdmin) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.checkUserRole(int userId, Role superAdmin)");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user;
        try {
            user = userDao.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error occurred in checkUserRole() method", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("checkUserRole:" + user.toString());
        }

        return (user.getRole().equals(superAdmin)) && user.isStatus() != false;
    }

    @Override
    public boolean checkUserRole(int userId, Role superAdmin, Role admin) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.checkUserRole(int userId, Role superAdmin, Role admin)");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user;
        try {
            user = userDao.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error occurred in checkUserRole() method", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("checkUserRole:" + user.toString());
        }

        return (user.getRole().equals(superAdmin) || user.getRole().equals(admin)) && user.isStatus() != false;
    }

    @Override
    public boolean checkUserRole(int userId, Role superAdmin, Role admin, Role userRole) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.checkUserRole(int userId, Role superAdmin, Role admin, Role userRole)");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();
        User user;
        try {
            user = userDao.getUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("Error occurred in checkUserRole() method", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("checkUserRole:" + user.toString());
        }
        return (user.getRole().equals(superAdmin) || user.getRole().equals(admin) || user.getRole().equals(userRole)) && user.isStatus() != false;
    }

    @Override
    public void changeUserBlockStatus(int userId, boolean blockStatus) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.changeUserBlockStatus()");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {
            User userFromDb = userDao.getUser(userId);
            if (userFromDb.getRole() == Role.USER) {
                userDao.changeUserBlockStatus(userId, blockStatus);
            } else {
                throw new ServiceException("You can't ban the user with status admin or super admin.");
            }

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in changeUserBlockStatus() method", e);
        }
    }

    @Override
    public void changeRole(int userId, Role role) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.changeRole()");
        }

        UserDao userDao = daoFactory.getMysqlUserImpl();

        try {


            User userFromDb = userDao.getUser(userId);
            if (checkUserRole(userFromDb, role)) {
                userDao.changeRole(userId, role);
            } else if (checkAdminRole(userFromDb, role)) {
                userDao.changeRole(userId, role);
            } else {
                throw new ServiceException("You can't change the role , because it isn't possible.");
            }


        } catch (DAOException e) {
            throw new ServiceException("Error occurred in changeRole() method", e);
        }
    }

    private void checkRegistrationParams(User user) throws UtilException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.checkRegistrationParams()");
        }

        Util.isNull(user.getLogin(), user.getPassword());
        Util.isEmptyString(user.getLogin(), user.getPassword());

    }

    private void checkLogin(User user) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ClientServiceImpl.checkLogin()");
            logger.debug(user.toString());
        }

        User checkedUser;

        try {
            checkedUser = getUser(user);
        } catch (ServiceException e) {
            throw new ServiceException("Error occurred in getUser() method", e);
        }
        if (checkedUser != null) {
            throw new ServiceException("Login exists");
        }
    }

    private boolean checkUserRole(User user, Role role) {
        boolean userRole = user.getRole() == Role.USER;
        boolean initRole = role == Role.ADMIN;
        boolean result = userRole && initRole;

        return result;
    }

    private boolean checkAdminRole(User user, Role role) {
        boolean userRole = user.getRole() == Role.ADMIN;
        boolean initRole = role == Role.USER;
        boolean result = userRole && initRole;

        return result;
    }
}
