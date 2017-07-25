package com.epam.tsylko.andrei.service.factory;


import com.epam.tsylko.andrei.service.ClientService;
import com.epam.tsylko.andrei.service.LibraryService;
import com.epam.tsylko.andrei.service.OrdersService;
import com.epam.tsylko.andrei.service.ResidenceService;
import com.epam.tsylko.andrei.service.impl.ClientServiceImpl;
import com.epam.tsylko.andrei.service.impl.LibraryServiceImpl;
import com.epam.tsylko.andrei.service.impl.OrdersServiceImpl;
import com.epam.tsylko.andrei.service.impl.ResidenceServiceImpl;

public final class ServiceFactory {
//    eagerly created instance
//    private static final ServiceFactory instance = new ServiceFactory();
//
//    private final ClientService clientService = new ClientServiceImpl();
//    private final LibraryService libraryService = new LibraryServiceImpl();
//    private final OrdersService ordersService = new OrdersServiceImpl();
//    private final ResidenceService residenceService = new ResidenceServiceImpl();
//
//    private ServiceFactory(){}
//
//    public static ServiceFactory getInstance(){
//        return instance;
//    }
//
//    public ClientService getClientService() {
//        return clientService;
//    }
//
//    public LibraryService getLibraryService() {
//        return libraryService;
//    }
//
//    public OrdersService getOrdersService() {
//        return ordersService;
//    }
//
//    public ResidenceService getResidenceService() {
//        return residenceService;
//    }

    private final ClientService clientService = new ClientServiceImpl();
    private final LibraryService libraryService = new LibraryServiceImpl();
    private final OrdersService ordersService = new OrdersServiceImpl();
    private final ResidenceService residenceService = new ResidenceServiceImpl();
    private ServiceFactory(){}
    //Lazy loading
    private static class SingletonHolder{
        public static final ServiceFactory instance = new ServiceFactory();
    }

    public static ServiceFactory getInstance(){
        return SingletonHolder.instance;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public LibraryService getLibraryService() {
        return libraryService;
    }

    public OrdersService getOrdersService() {
        return ordersService;
    }

    public ResidenceService getResidenceService() {
        return residenceService;
    }
}
