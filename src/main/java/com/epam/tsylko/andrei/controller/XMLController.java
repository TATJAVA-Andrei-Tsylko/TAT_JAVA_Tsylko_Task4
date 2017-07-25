package com.epam.tsylko.andrei.controller;


import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import org.apache.log4j.Logger;

public class XMLController {


    private final static Logger logger = Logger.getLogger(XMLController.class);
    private final static String DOM_PARSER = "DOM";
    private final static String SAX_PARSER = "SAX";
    private final static String STAX_PARSER = "STAX";
    private String parser;

    private XmlCommandProvider provider = new XmlCommandProvider();

    private final char paramDelimiter = '&';
    private final char start = '=';

    public void setParser(String parser) {
        switch (parser) {
            case SAX_PARSER:
                provider.setParser(SAX_PARSER);
                break;
            case STAX_PARSER:
                provider.setParser(STAX_PARSER);
                break;

            default:
                provider.setParser(DOM_PARSER);
        }

    }

    public String executeTask(String request) {
        String commandName;
        Command executionCommand;
        String response;

        try {
            if (ControllerUtil.checkRequestLinkWithoutParams(request)) {

                commandName = request.substring(request.indexOf(start) + 1, request.length());

                if (logger.isDebugEnabled()) {
                    logger.debug("Command without params: " + commandName);
                }

            } else {

                ControllerUtil.checkRequestLink(request);
                commandName = request.substring(request.indexOf(start) + 1, request.indexOf(paramDelimiter));

                if (logger.isDebugEnabled()) {
                    logger.debug("Command with params: " + commandName);
                }

            }

            if (logger.isDebugEnabled()) {
                logger.debug(commandName);
            }


            executionCommand = provider.getCommand(commandName);

            if (executionCommand.getAccess(request)) {

                if (logger.isDebugEnabled()) {
                    logger.debug("Access level is applied");
                }

                response = executionCommand.execute(request);

            } else {

                if (logger.isDebugEnabled()) {
                    logger.debug("Access denied: " + request);
                }

                response = "Access denied";
            }
        } catch (ControllerUtilException e) {
            logger.error("Invalid link " + request);
            response = "Invalid link";
        }

        return response;
    }


}
