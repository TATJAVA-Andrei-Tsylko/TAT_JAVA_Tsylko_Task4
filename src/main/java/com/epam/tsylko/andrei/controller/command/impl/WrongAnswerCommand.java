package com.epam.tsylko.andrei.controller.command.impl;


import com.epam.tsylko.andrei.controller.command.Command;

public class WrongAnswerCommand implements Command {
    @Override
    public String execute(String request) {
        return "Error";
    }

    @Override
    public boolean getAccess(String request) {
        return true;
    }
}
