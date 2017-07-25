package com.epam.tsylko.andrei.controller.builder.xml.parser;


import com.epam.tsylko.andrei.controller.util.ControllerUtilException;

import java.util.List;

interface Builder<T> {
    List<T> build(String file) throws ControllerUtilException;
}
