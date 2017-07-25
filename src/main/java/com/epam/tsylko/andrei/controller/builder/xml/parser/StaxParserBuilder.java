package com.epam.tsylko.andrei.controller.builder.xml.parser;


import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Command;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StaxParserBuilder implements Builder<Command> {
    private static Logger logger = Logger.getLogger(StaxParserBuilder.class);
    private final static String COMMAND = "command";
    private final static String COMMAND_NAME = "commandName";
    private final static String COMMAND_CLASS = "commandClass";

    @Override
    public List<Command> build(String file) throws ControllerUtilException {
        List<Command> list = new ArrayList<>();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        list = buildCommand(file, list, inputFactory);
        return list;
    }

    private static List<Command> buildCommand(String fileName, List<Command> commands, XMLInputFactory inputFactory) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(COMMAND)) {
                        try {
                            Command command = buildCommand(reader);
                            commands.add(command);

                        } catch (XMLStreamException e) {

                            logger.error("Error in method StaxParserBuilder.buildCommand()", e);
                        }
                    }

                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            logger.error("Error StaxParserBuilder.buildCommand(). Cannot find file or XMLStreamException", e);
        }
        return commands;
    }

    private static Command buildCommand(XMLStreamReader reader) throws XMLStreamException {
        Command command = new Command();
        String name = "";
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (name) {
                        case COMMAND:
                            command = new Command();
                            break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText().trim();
                    if (text.isEmpty()) {
                        break;
                    }
                    switch (name) {
                        case COMMAND_NAME:
                            command.setCommandName(text);
                            break;
                        case COMMAND_CLASS:
                            command.setCommandClass(text);
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals(COMMAND)) {
                        return command;
                    }
                    break;

            }
        }
        throw new XMLStreamException("Unknown element in tag command");
    }
}
