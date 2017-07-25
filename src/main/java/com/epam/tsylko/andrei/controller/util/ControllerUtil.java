package com.epam.tsylko.andrei.controller.util;


import com.epam.tsylko.andrei.controller.builder.AddressBuilder;
import com.epam.tsylko.andrei.controller.builder.BookBuilder;
import com.epam.tsylko.andrei.controller.builder.Director;
import com.epam.tsylko.andrei.controller.builder.OrdersRepositoryBuilder;
import com.epam.tsylko.andrei.controller.builder.UserBuilder;
import com.epam.tsylko.andrei.entity.Address;
import com.epam.tsylko.andrei.entity.Book;
import com.epam.tsylko.andrei.entity.OrdersRepository;
import com.epam.tsylko.andrei.entity.Role;
import com.epam.tsylko.andrei.entity.User;
import org.apache.log4j.Logger;


import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerUtil {
    private final static Logger logger = Logger.getLogger(ControllerUtil.class);
    private final static String ORDER_DESC = "DESC";
    private final static String ORDER_ASC = "ASC";
    private final static String REQUEST = "^action=.+&.+";
    private final static String REQUEST_WITHOUT_PARAMS = "^action=.+";
    private final static String REQUEST_WITHOUT_AMP = "^((?!&).)*$";
    private final static String ID = "&id=";
    private final static String BOOLEAN_TRUE = "true";
    private final static String BOOLEAN_FALSE = "false";
    private final static String AMP = "[&]";
    private final static String EQUAL = "[=]";
    private final static String DATE_PATTERN = "yyyy-MM-dd";
    private final static String DELIMITER_DATA_BEFORE = "-";
    private final static String DELIMITER_DATA_AFTER = "[\\./]";
    private final static String ORDER = "order";
    private final static String ROLE = "role";
    private final static String USER_ID = "userId";
    private final static String ENABLE = "status";


    public final static Book initBookObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.initBookObj()");
        }

        Director<Book> director = new Director<Book>(new BookBuilder());
        Book book = director.createEntity(dataFromRequest);
        return book;
    }

    public final static Address initAddressObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.initAddressObj");
        }

        Director<Address> director = new Director<Address>(new AddressBuilder());
        Address address = director.createEntity(dataFromRequest);
        return address;
    }

    public final static User initUserObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.initUserObj");
        }

        Director<User> director = new Director<User>(new UserBuilder());
        User user = director.createEntity(dataFromRequest);
        return user;
    }

    public final static OrdersRepository initOrderObj(Map<String, String> dataFromRequest) throws ControllerUtilException {
        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.initOrderObj");
        }
        Director<OrdersRepository> director = new Director<>(new OrdersRepositoryBuilder());
        OrdersRepository repository = director.createEntity(dataFromRequest);
        return repository;
    }

    public final static User initUserObjWithRoleParam(Map<String, String> dataFromRequest) throws ControllerUtilException {
        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.initUserObjWithRoleParam");
        }

        User user;

        if (getValueFromMapByKey(dataFromRequest, ROLE) != null &&
                Role.getByName(getValueFromMapByKey(dataFromRequest, ROLE)) != Role.SUPER_ADMIN) {
            try {

                user = new User(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, USER_ID)),
                        Role.getByName(getValueFromMapByKey(dataFromRequest, ROLE)));

            } catch (NumberFormatException e) {
                throw new ControllerUtilException("Numbers isn't parsable in method initUserObjWithRoleParam");
            }
        } else {
            throw new ControllerUtilException("Request doesn't content role");
        }
        return user;
    }


    public final static User initUserObjWithBlockParam(Map<String, String> dataFromRequest) throws ControllerUtilException {
        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.initUserObjWithBlockParam");
        }

        User user;
        if (getValueFromMapByKey(dataFromRequest, ENABLE) != null) {
            try {

                user = new User(parseNumberOrNullStringToInt(getValueFromMapByKey(dataFromRequest, USER_ID)),
                        parseBooleanValueFromString(getValueFromMapByKey(dataFromRequest, ENABLE)));

            } catch (NumberFormatException e) {
                throw new ControllerUtilException("Numbers isn't parsable in method initUserObjWithBlockParam");
            }
        } else {
            throw new ControllerUtilException("Request doesn't content user enable status");
        }
        return user;
    }


    public final static boolean checkRequestLink(String request) throws ControllerUtilException {

        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.checkRequestLink()");
        }

        boolean result = false;
        if (request == null || request.isEmpty()) {

        } else {
            Pattern pattern = Pattern.compile(REQUEST);

            Matcher matcher = pattern.matcher(request);
            result = matcher.matches();
            if (!result) {
                throw new ControllerUtilException("Invalid request.");
            }

        }


        return result;
    }

    public final static boolean checkRequestLinkWithoutParams(String request) throws ControllerUtilException {
        boolean result = false;
        Pattern pattern = Pattern.compile(REQUEST_WITHOUT_AMP);

        Matcher matcher = pattern.matcher(request);

        Pattern patternForParams = Pattern.compile(REQUEST_WITHOUT_PARAMS);

        Matcher matcherForParams = patternForParams.matcher(request);
        if (matcherForParams.matches() && matcher.matches()) {
            result = matcherForParams.matches();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.checkRequestLinkWithoutParams(): " + result);
        }

        return result;
    }

    public final static boolean castRequestToMapToBoolean(Map<String, String> requestMap, String key) throws ControllerUtilException {
        String requestFromMap = getValueFromMapByKey(requestMap, key);
        boolean result = parseBooleanValueFromString(requestFromMap);
        return result;
    }

    public final static Map<String, String> castRequestParamToMap(String request) throws ControllerUtilException {
        Map<String, String> requestParams = new HashMap<>();
        if (request != null) {

            for (String pair : splitAmp(request)) {
                String params[] = splitEqual(pair);
                if (params.length != 2) {
                    throw new ControllerUtilException("Check params in request");
                }
                requestParams.put(params[0], params[1]);
            }
        }
        return requestParams;

    }

//check, how works with user status
    public static boolean parseBooleanValueFromString(String param) throws ControllerUtilException {
        if (param != null && (param.equalsIgnoreCase(BOOLEAN_FALSE) || param.equalsIgnoreCase(BOOLEAN_TRUE))) {
            return Boolean.valueOf(param);
        } else if (param == null) {
            return false;
        } else {
            throw new ControllerUtilException("Check inputed boolean value");
        }

    }


    public static String getValueFromMapByKey(Map<String, String> requestMap, String key) {
        String value = requestMap.entrySet().stream()
                .filter(e -> e.getKey().equals(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        return value;
    }

    public static final int parseStringToIntFromMap(Map<String, String> requestMap, String key) {
        String number = getValueFromMapByKey(requestMap, key);
        int result = parseNumberOrNullStringToInt(number);
        return result;

    }

    public static final java.sql.Date parseSQLDateFromMap(Map<String, String> requestMap, String key) throws ControllerUtilException {
        String dateString = getValueFromMapByKey(requestMap, key);
        java.sql.Date date = castStringToSqlDate(dateString);
        return date;
    }

    public final static int findUserIdInRequest(String request) throws ControllerUtilException {
        int end = request.length();
        String id = request.substring(request.indexOf(ID) + 4, end);
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ControllerUtilException("UserId isn't parsable");
        }

        return userId;
    }


    public final static String getSortOrderFromRequest(Map<String, String> dataFromRequest) throws ControllerUtilException {
        if (logger.isDebugEnabled()) {
            logger.debug("ControllerUtil.getSortOrderFromRequest()");
        }

        String order = getValueFromMapByKey(dataFromRequest, ORDER);
        if (order.equalsIgnoreCase(ORDER_ASC)) {
            return ORDER_ASC;
        } else if (order.equalsIgnoreCase(ORDER_DESC)) {
            return ORDER_DESC;
        } else {
            throw new ControllerUtilException("Incorrect value for order sort " + order);
        }

    }


    private static int parseNumberOrNullStringToInt(String param) {

        int number = 0;
        try {
            if (param != null)
                number = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            number = 0;
        }
        return number;
    }


    private static String[] splitAmp(String request) {
        return request.split(AMP);
    }

    private static String[] splitEqual(String request) {
        return request.split(EQUAL);
    }

    private final static java.sql.Date castStringToSqlDate(String time) throws ControllerUtilException {
        java.util.Date date;
        if (time != null && !time.isEmpty()) {
            time = time.replaceAll(DELIMITER_DATA_AFTER, DELIMITER_DATA_BEFORE);
            java.text.DateFormat format = new java.text.SimpleDateFormat(DATE_PATTERN);

            try {
                date = format.parse(time);
            } catch (ParseException e) {
                throw new ControllerUtilException("Error in method castStringToSqlDate. Cannot cast date from String to SQLDate", e);
            }
        } else {
            date = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            if (logger.isDebugEnabled()) {
                logger.debug("Date: " + date);
            }
        }

        return new java.sql.Date(date.getTime());
    }


}
