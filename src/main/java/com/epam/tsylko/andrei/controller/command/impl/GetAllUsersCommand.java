package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Role;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;


public class GetAllUsersCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetAllUsersCommand.class);


    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("GetAllUsersCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        List<User> users;
        try {

            users = service.getAllUsers();

            if (logger.isDebugEnabled()) {
                logger.debug(users.toString());
            }

            response = "There are " + users.size() + " users.";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "There are no users now.";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        boolean access = false;
        if (logger.isDebugEnabled()) {
            logger.debug("GetAllUsersCommand.getAccess()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();
        try {
            int userId = ControllerUtil.findUserIdInRequest(request);
            access = clientService.checkUserRole(userId, Role.SUPER_ADMIN, Role.ADMIN);
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
        } catch (ControllerUtilException e) {
            logger.error("Error in ControllerUtil ", e);
        }
        return access;
    }
}
