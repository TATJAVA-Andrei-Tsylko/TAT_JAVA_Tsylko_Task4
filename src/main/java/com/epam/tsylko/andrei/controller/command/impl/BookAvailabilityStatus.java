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

public class BookAvailabilityStatus implements Command {
    private final static String BOOK_ACCESSIBILITY_KEY = "isAvailable";
    private final static Logger logger = Logger.getLogger(BookAvailabilityStatus.class);

    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("BookAvailabilityStatus.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LibraryService service = serviceFactory.getLibraryService();
        Map<String, String> book;

        try {
            book = ControllerUtil.castRequestParamToMap(request);
            Book existedBook = ControllerUtil.initBookObj(book);

            if (logger.isDebugEnabled()) {
                logger.debug(existedBook.toString());
            }
            if (existedBook.getId() > 0 && book.containsKey(BOOK_ACCESSIBILITY_KEY)) {
                String validBook = ControllerUtil.getValueFromMapByKey(book, BOOK_ACCESSIBILITY_KEY);
                service.makeBookUnAvailable(existedBook.getId(), ControllerUtil.parseBooleanValueFromString(validBook));
                response = "Status was changed";
            } else{
                response = "request doesn't content key status or bookId";
            }


        } catch (ServiceException e) {
            response = "Incorrect request";
            logger.error("request params " + BookAvailabilityStatus.class.getName() + " was incorrect: " + request, e);
        } catch (ControllerUtilException e) {
            logger.error("Error in service layer", e);
            response = "Error during changing books status";
        }

        return response;
    }

    @Override
    public boolean getAccess(String request) {
        boolean access = false;

        if (logger.isDebugEnabled()) {
            logger.debug("BookAvailabilityStatus.getAccess()");
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
