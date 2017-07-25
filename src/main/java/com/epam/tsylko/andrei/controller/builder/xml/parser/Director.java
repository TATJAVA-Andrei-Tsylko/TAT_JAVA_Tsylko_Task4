package com.epam.tsylko.andrei.controller.builder.xml.parser;


import com.epam.tsylko.andrei.controller.util.ControllerUtilException;

import java.util.List;

public class Director<T> {
    private Builder<T> builder;

    public Director(Builder<T> builder) {
        this.builder = builder;
    }

    public List<T> createEntity(String file) throws ControllerUtilException {
        List<T> list = builder.build(file);
        return list;
    }
}
