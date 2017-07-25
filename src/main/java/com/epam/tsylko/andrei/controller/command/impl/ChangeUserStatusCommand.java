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


public class ChangeUserStatusCommand implements Command {
    private final static Logger logger = Logger.getLogger(ChangeUserStatusCommand.class);


    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("ChangeUserStatusCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        Map<String, String> user;

        try {
            user = ControllerUtil.castRequestParamToMap(request);
            User userStatus = ControllerUtil.initUserObjWithBlockParam(user);

            if (logger.isDebugEnabled()) {
                logger.debug(userStatus.toString());
            }

            service.changeUserBlockStatus(userStatus.getId(), userStatus.isStatus());
            response = "Status was changed";
        } catch (ControllerUtilException e) {
            logger.error("request params " + ChangeUserStatusCommand.class.getName() + " was incorrect: " + request, e);
            response = "Incorrect request";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during change status procedure";
        }
        return response;

    }

    @Override
    public boolean getAccess(String request) {
        boolean access = false;

        if (logger.isDebugEnabled()) {
            logger.debug("ChangeUserStatusCommand.getAccess()");
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
