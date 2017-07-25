package com.epam.tsylko.andrei.controller.builder.xml.parser;


import com.epam.tsylko.andrei.entity.Command;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SaxHandler extends DefaultHandler {
    private final static String COMMAND = "command";
    private final static String COMMAND_NAME = "commandName";
    private final static String COMMAND_CLASS = "commandClass";
    private StringBuilder text;
    private List<Command> commandContainer;
    private Command command;

    public SaxHandler() {
        this.commandContainer = new ArrayList<>();
    }

    public List<Command> getCommandContainer() {
        return commandContainer;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        text = new StringBuilder();
        if (qName.equals(COMMAND)) {
            command = new Command();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case COMMAND_NAME:
                command.setCommandName(text.toString());
                break;
            case COMMAND_CLASS:
                command.setCommandClass(text.toString());
                break;
        }
        if (localName.equals(COMMAND) && command != null) {
            commandContainer.add(command);
        }

    }
}
