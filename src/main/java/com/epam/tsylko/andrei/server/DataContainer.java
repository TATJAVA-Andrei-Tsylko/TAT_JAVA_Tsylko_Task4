package com.epam.tsylko.andrei.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataContainer {
    private BlockingQueue<Worker> queue = new LinkedBlockingQueue<>();

    public void put(Worker data) {
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BlockingQueue<Worker> getQueue() {
        return queue;
    }

    public Worker take() {
        Worker data = null;
        try {
            data = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

}
