package com.epam.tsylko.andrei.controller;


import com.epam.tsylko.andrei.controller.builder.xml.parser.Director;
import com.epam.tsylko.andrei.controller.builder.xml.parser.DomParserBuilder;
import com.epam.tsylko.andrei.controller.builder.xml.parser.SaxParserBuilder;
import com.epam.tsylko.andrei.controller.builder.xml.parser.StaxParserBuilder;
import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlCommandProvider {
    private static Logger logger = Logger.getLogger(XmlCommandProvider.class);
    private final static String DOM_PARSER = "DOM";
    private final static String SAX_PARSER = "SAX";
    private final static String STAX_PARSER = "STAX";

    private Map<String, Command> repository;
    private List<com.epam.tsylko.andrei.entity.Command> commands;

    public XmlCommandProvider() {

    }

    public void setParser(String parser) {
        String file = "C:\\Users\\Administrator\\IdeaProjects\\Library\\src\\main\\resources\\command.xml";
        Director<com.epam.tsylko.andrei.entity.Command> director = null;
        switch (parser) {
            case DOM_PARSER:
                director = new Director<>(new DomParserBuilder());
                break;
            case SAX_PARSER:
                director = new Director<>(new SaxParserBuilder());
                break;
            case STAX_PARSER:
                director = new Director<>(new StaxParserBuilder());
                break;
        }
        try {
            commands = director.createEntity(file);
        } catch (ControllerUtilException e) {
            logger.error("Cannot init parser", e);
        }

        initMap();

    }

    private void initMap() {

        repository = commands.stream().collect(HashMap<String, Command>::new, (m, c) -> {
            try {
                m.put(c.getCommandName(), (Command) Class.forName("com.epam.tsylko.andrei.controller.command.impl." + c.getCommandClass()).newInstance());
            } catch (InstantiationException e) {
                logger.error("InstantiationException in XmlCommandProvider.initMap()",e);
            } catch (IllegalAccessException e) {
                logger.error("IllegalAccessException in XmlCommandProvider.initMap()",e);
            } catch (ClassNotFoundException e) {
               logger.error("ClassNotFoundException in XmlCommandProvider.initMap()",e);
            }
        }, (m, u) -> {
        });

//        repository.forEach((key, value) -> {
//            System.out.println("Key : " + key + " Value : " + value);
//        });
    }


    Command getCommand(String name) {
        Command command = null;

        try {
            command = repository.get(name);
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error(name + " is invalid.", e);
            command = repository.get(CommandName.WRONG_REQUEST);
        }

        if(logger.isDebugEnabled()){
            logger.debug("Command: " + command);
        }

        return command;
    }
}
