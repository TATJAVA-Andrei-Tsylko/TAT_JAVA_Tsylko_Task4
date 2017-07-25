package com.epam.tsylko.andrei.entity;


public class Command {
    private String commandName;
    private String commandClass;

    public Command() {
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandClass() {
        return commandClass;
    }

    public void setCommandClass(String commandClass) {
        this.commandClass = commandClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;

        Command command = (Command) o;

        if (commandName != null ? !commandName.equals(command.commandName) : command.commandName != null) return false;
        return commandClass != null ? commandClass.equals(command.commandClass) : command.commandClass == null;
    }

    @Override
    public int hashCode() {
        int result = commandName != null ? commandName.hashCode() : 0;
        result = 31 * result + (commandClass != null ? commandClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandName='" + commandName + '\'' +
                ", commandClass='" + commandClass + '\'' +
                '}';
    }
}
