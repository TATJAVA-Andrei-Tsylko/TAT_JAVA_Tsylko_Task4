package com.epam.tsylko.andrei.controller.builder.xml.parser;


import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Command;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaxParserBuilder implements Builder<Command> {
    private static Logger logger = Logger.getLogger(SaxParserBuilder.class);

    @Override
    public List<Command> build(String file) throws ControllerUtilException {
        if(logger.isDebugEnabled()){
            logger.debug("SaxParserBuilder.build()");
        }
        List<Command> commandList = new ArrayList<>();
        SaxHandler handler = new SaxHandler();
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
            reader.parse(file);
            commandList = handler.getCommandContainer();
        } catch (SAXException e) {
            logger.error("Error SaxParserBuilder.build(). SAXException", e);
        } catch (IOException e) {
            logger.error("Error SaxParserBuilder.build(). Cannot find file", e);
        }
        return commandList;
    }
}
