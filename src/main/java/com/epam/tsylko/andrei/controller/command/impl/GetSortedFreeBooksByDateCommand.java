package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import java.util.List;

public class GetSortedFreeBooksByDateCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetSortedFreeBooksByDateCommand.class);


    @Override
    public String execute(String request) {
        String response;

        if (logger.isDebugEnabled()) {
            logger.debug("GetSortedFreeBooksByDateCommand.execute()");
        }

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        LibraryService service = serviceFactory.getLibraryService();
        List<Book> books;

        try {
            books = service.sortFreeBooksByDate(ControllerUtil.getSortOrderFromRequest(ControllerUtil.castRequestParamToMap(request)));

            if (logger.isDebugEnabled()) {
                logger.debug(books.toString());
            }

            response = "Free books size " + books.size();
        } catch (ServiceException e) {
            logger.error("Error occurred in service layer", e);
            response = "There are no books now.";
        } catch (ControllerUtilException e) {
            logger.error("request params " + GetSortedFreeBooksByDateCommand.class.getName() + " was incorrect: " + request, e);

            response = "Error during sorting operation";
        }

        return response;

    }


    @Override
    public boolean getAccess(String request) {
        if (logger.isDebugEnabled()) {
            logger.debug("GetSortedFreeBooksByDateCommand.getAccess()");
        }
        return true;
    }
}
