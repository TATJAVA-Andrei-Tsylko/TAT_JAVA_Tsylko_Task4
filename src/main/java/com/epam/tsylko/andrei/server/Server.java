package com.epam.tsylko.andrei.server;


import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Server implements Runnable {
    private final static Logger logger = Logger.getLogger(Server.class);

    private static final Server instance = new Server();

    private DataContainer container = new DataContainer();
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private Thread thread = new Thread(this);
    private boolean isStopped = false;


    private Server() {
    }

    public static Server getInstance() {
        return instance;
    }


    public void start() {
        thread.start();
    }

    public void stop() {
        this.isStopped = true;
    }


    @Override
    public void run() {
        while (!isStopped) {
            startCommandExecute();
        }
    }

    public void processRequest(String request) {

        if (logger.isDebugEnabled()) {
            logger.debug("Server.processRequest()");
        }

        try {
            queue.put(request);
        } catch (InterruptedException e) {
           logger.error("Some error occurred in queue",e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Queue size():" + queue.size());
        }
    }

    private void startCommandExecute() {
        if (logger.isDebugEnabled()) {
            logger.debug("Server.startCommandExecute()");
            logger.debug("Server.startCommandExecute() -> queue size: " + queue.size());
        }
        if (queue.size() != 0 && !queue.isEmpty()) {
            try {
                storeResult();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Can't execute thread", e);
            }
        }
    }


    public Worker getRequestResponse() {
        Worker worker;
        if (logger.isDebugEnabled()) {
            logger.debug("Server.getRequestResponse(). Container size BEFORE: " + container.getQueue().size());
        }
        worker = container.take();

        if (logger.isDebugEnabled()) {
            logger.debug("Server.getRequestResponse(). Container size AFTER: " + container.getQueue().size());
        }

        return worker;
    }


    private void storeResult() throws InterruptedException, ExecutionException {
        if (logger.isDebugEnabled()) {
            logger.debug("Server.storeResult()");
        }
        List<Future<Worker>> list = executeRequests();
        for (Future<Worker> f : list) {
            container.put(extractValueFromFuture(f));
            if (logger.isDebugEnabled()) {
                logger.debug("Server.storeResult(). Container size: " + container.getQueue().size());
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("List size storeResult(): " + list.size());
        }
    }


    private List<Future<Worker>> executeRequests() throws InterruptedException {

        if (logger.isDebugEnabled()) {
            logger.debug("Server.executeRequests()");
        }
        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<Worker>> futures = service.invokeAll(getRequests());
        service.shutdown();
        return futures;
    }


    private List<Callable<Worker>> getRequests() {
        if (logger.isDebugEnabled()) {
            logger.debug("Server.getRequests()");
        }
        List<String> list = castQueueToList(queue);
        List<Callable<Worker>> callables = list.stream().map(item -> new Worker(item)).collect(Collectors.toList());
        return callables;
    }


    private <T> T extractValueFromFuture(Future<T> future) {

        if (logger.isDebugEnabled()) {
            logger.debug("Server.extractValueFromFuture()");
        }

        T val = null;
        try {
            val = future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Cannot extract Future value", e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Server.extractValueFromFuture() -> value : " + val.toString());
        }
        return val;
    }

    private List<String> castQueueToList(BlockingQueue<String> q) {
        List<String> list = new ArrayList<>();
        while (queue.size() != 0) {
            try {
                list.add(q.take());
            } catch (InterruptedException e) {
                logger.error("Cannot cast queue to list", e);
            }
        }
        return list;
    }


}
