package com.epam.tsylko.andrei.server;

import com.epam.tsylko.andrei.controller.Controller;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class Worker implements Callable<Worker> {

    private final static Logger logger = Logger.getLogger(Worker.class);

    private String request;
    private String response;

    public Worker(String msg) {
        this.request = msg;

    }

    public static Logger getLogger() {
        return logger;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public Worker call() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Thread name: " + Thread.currentThread().getName() + "; request: " + request);
        }

        Controller controller = new Controller();
        response = controller.executeTask(request);

        if (logger.isDebugEnabled()) {
            logger.debug("Thread name: " + Thread.currentThread().getName() + "; response: " + response);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "request='" + request + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
