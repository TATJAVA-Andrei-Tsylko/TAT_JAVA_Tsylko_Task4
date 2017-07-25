package com.epam.tsylko.andrei.service.impl;

import com.epam.tsylko.andrei.dao.AddressDao;
import com.epam.tsylko.andrei.dao.exception.DAOException;
import com.epam.tsylko.andrei.dao.factory.DAOFactory;
import com.epam.tsylko.andrei.entity.Address;
import com.epam.tsylko.andrei.service.ResidenceService;
import com.epam.tsylko.andrei.service.exception.ServiceException;
import com.epam.tsylko.andrei.service.util.Util;
import com.epam.tsylko.andrei.service.util.exception.UtilException;
import org.apache.log4j.Logger;


public class ResidenceServiceImpl implements ResidenceService {

    private final static Logger logger = Logger.getLogger(ResidenceServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public void enterHomeAddress(Address address) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ResidenceServiceImpl.enterHomeAddress()");
        }

        AddressDao dao = daoFactory.getMysqlAddressDao();
        try {

            if (checkInputtedAddressData(address)) {

                synchronized (this) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("ResidenceServiceImpl.enterHomeAddress() -> synchronized");
                    }
                    dao.addAddressToUser(address);
                }
            }

        } catch (UtilException e) {
            throw new ServiceException("Incorrect values. This values don't math to address object", e);
        } catch (DAOException e) {
            throw new ServiceException("Error occurred in enterHomeAddress() method in service layer ResidenceServiceImpl", e);
        }

    }

    @Override
    public void updateHomeAddress(Address address) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ResidenceServiceImpl.updateHomeAddress()");
        }

        AddressDao dao = daoFactory.getMysqlAddressDao();
        try {

            if (checkInputtedAddressData(address)) {

                synchronized (this) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("ResidenceServiceImpl.updateHomeAddress() -> synchronized");
                    }
                    dao.updateUsersAddress(address);
                }

            }

        } catch (UtilException e) {
            throw new ServiceException("Incorrect values. This values don't math to address object", e);

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in updateHomeAddress() method in service layer ResidenceServiceImpl", e);
        }
    }

    @Override
    public Address getCurrentAddress(int addressId) throws ServiceException {

        if (logger.isDebugEnabled()) {
            logger.debug("ResidenceServiceImpl.getCurrentAddress()");
        }

        AddressDao dao = daoFactory.getMysqlAddressDao();
        Address address;
        try {

            address = dao.getAddress(addressId);

        } catch (DAOException e) {
            throw new ServiceException("Error occurred in getCurrentAddress() method in service layer ResidenceServiceImpl", e);
        }

        return address;
    }

    private boolean checkInputtedAddressData(Address address) throws UtilException {
        Util.isNull(address.getCountry(), address.getCity(), address.getStreet());
        Util.isEmptyString(address.getCountry(), address.getCity(), address.getStreet());
        Util.isNumberPositive(address.getHouse(), address.getRoom());
        return true;
    }
}
