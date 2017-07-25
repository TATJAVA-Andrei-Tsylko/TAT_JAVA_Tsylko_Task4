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

import java.util.Map;

public class GetUserCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserCommand.class);

    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("GetUserCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        Map<String, String> user;

        try {
            user = ControllerUtil.castRequestParamToMap(request);
            User userFromDB = service.getUser(ControllerUtil.initUserObj(user).getId());

            if (logger.isDebugEnabled()) {
                logger.debug(userFromDB.toString());
            }

            response = userFromDB.toString();
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "User was not found.";
        } catch (ControllerUtilException e) {
            logger.error("request params " + GetUserCommand.class.getName() + " was incorrect: " + request, e);
            response = "Error during retrieving user";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        boolean access = false;

        if (logger.isDebugEnabled()) {
            logger.debug("GetUserCommand.getAccess()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService clientService = serviceFactory.getClientService();

        try {
            int userId = ControllerUtil.findUserIdInRequest(request);
            access = clientService.checkUserRole(userId, Role.SUPER_ADMIN, Role.ADMIN, Role.USER);
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
        } catch (ControllerUtilException e) {
            logger.error("Error in ControllerUtil ", e);
        }
        return access;
    }
}
