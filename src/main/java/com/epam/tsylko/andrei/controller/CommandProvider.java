package com.epam.tsylko.andrei.controller;

import com.epam.tsylko.andrei.controller.command.Command;
import com.epam.tsylko.andrei.controller.command.impl.*;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public final class CommandProvider {
    private static Logger logger = Logger.getLogger(CommandProvider.class);

    private final Map<CommandName, Command> repository = Collections.synchronizedMap(new HashMap<>());

    private CommandProvider() {
        repository.put(CommandName.SIGN_IN, new SignInCommand());
        repository.put(CommandName.ADD_BOOK, new AddBookCommand());
        repository.put(CommandName.SHOW_ALL_BOOKS, new GetAllBooksCommand());
        repository.put(CommandName.EDIT_BOOK, new EditBookCommand());
        repository.put(CommandName.GET_BOOK, new GetBookCommand());
        repository.put(CommandName.BOOK_AVAILABILITY_STATUS, new BookAvailabilityStatus());
        repository.put(CommandName.HOME_ADDRESS, new AddHomeAddressCommand());
        repository.put(CommandName.EDIT_ADDRESS, new EditAddressCommand());
        repository.put(CommandName.CURRENT_ADDRESS, new GetCurrentAddressCommand());
        repository.put(CommandName.USER_REGISTRATION, new RegistrateCommand());
        repository.put(CommandName.USER_EDITED, new EditUserCommand());
        repository.put(CommandName.ALL_USERS, new GetAllUsersCommand());
        repository.put(CommandName.GET_USER, new GetUserCommand());
        repository.put(CommandName.USER_ROLE, new ChangeUserRoleCommand());
        repository.put(CommandName.USER_STATUS, new ChangeUserStatusCommand());
        repository.put(CommandName.BOOK_RESERVATION, new ReserveBookCommand());
        repository.put(CommandName.CANCELLATION_BOOK_RESERVATION, new CancelBookReservationCommand());
        repository.put(CommandName.BOOK_LEAVED_LIBRARY, new GetLivedLibraryBookCommand());
        repository.put(CommandName.BOOK_RETURNED_COMMAND, new ReceiveBookFromUserCommand());
        repository.put(CommandName.REDUCE_ACCESS_LEVEL_COMMAND, new ReduceAccessLevelCommand());
        repository.put(CommandName.SORTED_BOOKS, new GetSortedFreeBooksByDateCommand());
        repository.put(CommandName.WRONG_REQUEST, new WrongAnswerCommand());
    }

    public static class SingletonHolderForCommander {
        public final static CommandProvider instance = new CommandProvider();
    }

    public static CommandProvider getInstance() {
        return SingletonHolderForCommander.instance;
    }


    Command getCommand(String name) {
        CommandName commandName = null;
        Command command = null;

        try {
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error(commandName + " is invalid.", e);
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
