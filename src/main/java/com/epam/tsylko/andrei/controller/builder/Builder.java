package com.epam.tsylko.andrei.controller.builder;

import com.epam.tsylko.andrei.controller.util.ControllerUtilException;

import java.util.Map;

public interface Builder <T> {
    T build(Map<String, String> dataFromRequest) throws ControllerUtilException;
}
