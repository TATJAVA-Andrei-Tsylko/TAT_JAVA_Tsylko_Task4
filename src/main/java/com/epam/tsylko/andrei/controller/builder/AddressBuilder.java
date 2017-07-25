package com.epam.tsylko.andrei.controller.builder;

import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Address;



import java.util.Map;


public class AddressBuilder implements Builder<Address> {

    private final static String ADDRESS_ID = "addressId";
    private final static String ADDRESS_COUNTRY = "country";
    private final static String ADDRESS_CITY = "city";
    private final static String ADDRESS_STREET = "street";
    private final static String ADDRESS_HOUSE = "house";
    private final static String ADDRESS_ROOM = "room";

    @Override
    public Address build(Map<String, String> dataFromRequest) throws ControllerUtilException {
        Address address =new Address(ControllerUtil.parseStringToIntFromMap(dataFromRequest, ADDRESS_ID),
            ControllerUtil.getValueFromMapByKey(dataFromRequest, ADDRESS_COUNTRY),
            ControllerUtil.getValueFromMapByKey(dataFromRequest, ADDRESS_CITY),
            ControllerUtil.getValueFromMapByKey(dataFromRequest, ADDRESS_STREET),
            ControllerUtil.parseStringToIntFromMap(dataFromRequest, ADDRESS_HOUSE),
            ControllerUtil.parseStringToIntFromMap(dataFromRequest, ADDRESS_ROOM));
        return address;
    }
}
