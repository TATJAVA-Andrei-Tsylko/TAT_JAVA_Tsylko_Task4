package com.epam.tsylko.andrei.controller.builder;

import com.epam.tsylko.andrei.controller.util.ControllerUtilException;

import java.util.Map;

public class Director<T> {
    private Builder<T> builder;

    public Director(Builder<T> builder) {
        this.builder = builder;
    }

    public T createEntity(Map<String, String> dataFromRequest) throws ControllerUtilException {
        T t = builder.build(dataFromRequest);
        return t;
    }
}
