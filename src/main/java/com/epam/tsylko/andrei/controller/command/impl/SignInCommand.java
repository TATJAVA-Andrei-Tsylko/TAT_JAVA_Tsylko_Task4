package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.User;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Map;

public class SignInCommand implements Command {
    private final static Logger logger = Logger.getLogger(SignInCommand.class);

    @Override
    public String execute(String request) {
        String response;
        if (logger.isDebugEnabled()) {
            logger.debug("SignInCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        Map<String, String> user;

        try {
            user = ControllerUtil.castRequestParamToMap(request);
            User signInUser = ControllerUtil.initUserObj(user);

            if (logger.isDebugEnabled()) {
                logger.debug(signInUser.toString());
            }

            service.signIn(signInUser);
            response = "User was signed in";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during sign in operation.";
        } catch (ControllerUtilException e) {
            logger.error("request params " + SignInCommand.class.getName() + " was incorrect: " + request, e);
            response = "Error during retrieving user";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        if (logger.isDebugEnabled()) {
            logger.debug("SignInCommand.getAccess()");
        }
        return true;
    }
}
