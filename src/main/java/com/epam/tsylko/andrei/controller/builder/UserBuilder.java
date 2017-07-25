package com.epam.tsylko.andrei.controller.builder;

import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Address;
import com.epam.tsylko.andrei.entity.User;

import java.util.Map;


public class UserBuilder implements Builder<User> {
    private final static String USER_ID = "userId";
    private final static String USER_STATUS = "status";
    private final static String USER_LOGIN = "login";
    private final static String USER_PASSWORD = "password";
    private final static String USER_NAME = "userName";
    private final static String USER_SURNAME = "userSurname";
    private final static String USER_BIRTHDAY = "birthday";
    private final static String USER_EMAIL = "email";
    private final static String USER_PHONE = "phone";
    private final static String USER_ADDRESS = "address";


    @Override
    public User build(Map<String, String> dataFromRequest) throws ControllerUtilException {
        User user = new User(ControllerUtil.parseStringToIntFromMap(dataFromRequest, USER_ID),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, USER_LOGIN),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, USER_PASSWORD),
//                check this method. I don't add method status, that's why it doesn't affect on status in MySql
                ControllerUtil.castRequestToMapToBoolean(dataFromRequest,USER_STATUS),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, USER_NAME),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, USER_SURNAME),
                ControllerUtil.parseSQLDateFromMap(dataFromRequest, USER_BIRTHDAY),
                new Address(ControllerUtil.parseStringToIntFromMap(dataFromRequest, USER_ADDRESS)),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, USER_EMAIL),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, USER_PHONE));
        return user;
    }

}
