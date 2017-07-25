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

public class RegistrateCommand implements Command {
    private final static Logger logger = Logger.getLogger(RegistrateCommand.class);


    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("RegistrateCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ClientService service = serviceFactory.getClientService();
        Map<String, String> user;

        try {
            user = ControllerUtil.castRequestParamToMap(request);
            User newUser = ControllerUtil.initUserObj(user);

            if (logger.isDebugEnabled()) {
                logger.debug(newUser.toString());
            }

            service.registration(newUser);
            response = "User was added";
        } catch (ControllerUtilException e) {
            logger.error("request params " + RegistrateCommand.class.getName() + " was incorrect: " + request, e);
            response = "Incorrect request";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during registration procedure";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request){
    if (logger.isDebugEnabled()) {
        logger.debug("RegistrateCommand.getAccess()");
    }
        return true;
    }
}
