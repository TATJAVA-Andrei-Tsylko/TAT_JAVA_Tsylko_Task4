package com.epam.tsylko.andrei.controller.command;


public interface Command {
    String execute(String request);
    boolean getAccess(String request);
}
