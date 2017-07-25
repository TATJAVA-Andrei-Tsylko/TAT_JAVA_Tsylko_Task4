package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.entity.Role;
import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.Map;

public class EditBookCommand implements Command {
    private final static Logger logger = Logger.getLogger(AddBookCommand.class);


    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("EditBookCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LibraryService service = serviceFactory.getLibraryService();
        Map<String, String> book;

        try {
            book = ControllerUtil.castRequestParamToMap(request);
            Book editedBook = ControllerUtil.initBookObj(book);

            if (logger.isDebugEnabled()) {
                logger.debug(editedBook.toString());
            }

            service.addEditedBook(editedBook);
            response = "Book was edited";
        } catch (ControllerUtilException e) {
            logger.error("request params " + EditBookCommand.class.getName() + " was incorrect: " + request);
            response = "Incorrect request";
        } catch (ServiceException e) {
            logger.error("Error in service layer", e);
            response = "Error during edited book procedure";
        }
        return response;
    }

    @Override
    public boolean getAccess(String request) {
        boolean access = false;

        if (logger.isDebugEnabled()) {
            logger.debug("EditBookCommand.getAccess()");
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
