package com.epam.tsylko.andrei.controller.builder.xml.parser;


import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Command;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomParserBuilder implements Builder<Command> {
    private static Logger logger = Logger.getLogger(DomParserBuilder.class);
    private final static String COMMAND = "command";
    private final static String COMMAND_NAME = "commandName";
    private final static String COMMAND_CLASS = "commandClass";
    private final static int INDEX = 0;


    @Override
    public List<Command> build(String file) throws ControllerUtilException {
        if(logger.isDebugEnabled()){
            logger.debug("DomParserBuilder.build()");
        }
        List<Command> list = new ArrayList<>();
        DocumentBuilder docBuilder = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Cannot get docBuilder", e);
        }

        list = buildCommand(file,list,docBuilder);
        return list;
    }

    private static List<Command> buildCommand(String fileName, List<Command> commandContainer, DocumentBuilder docBuilder) {
        Document doc = null;
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList candiesList = root.getElementsByTagName(COMMAND);

            for (int i = 0; i < candiesList.getLength(); i++) {

                Element commandElement = (Element) candiesList.item(i);
                Command command = buildCommand(commandElement);
                commandContainer.add(command);

            }
        } catch (SAXException e) {
            logger.error("Error DomParserBuilder.buildCommand(). SAXException", e);

        } catch (IOException e) {
            logger.error("Error DomParserBuilder.buildCommand(). Cannot find file", e);

        }

        return commandContainer;

    }

    private static Command buildCommand(Element commandElement) {
        Command command = new Command();
        command.setCommandName(getElementTextContent(commandElement, COMMAND_NAME));
        command.setCommandClass(getElementTextContent(commandElement, COMMAND_CLASS));
        return command;
    }


    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(INDEX);
        String text = node.getTextContent();
        return text;
    }
}
