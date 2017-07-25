package com.epam.tsylko.andrei.controller.builder;


import com.epam.tsylko.andrei.controller.util.ControllerUtil;
import com.epam.tsylko.andrei.controller.util.ControllerUtilException;
import com.epam.tsylko.andrei.entity.Book;


import java.util.Map;


public class BookBuilder implements Builder<Book> {
    private final static String BOOK_ID = "bookId";
    private final static String BOOK_NAME = "booksName";
    private final static String BOOK_AUTHOR_NAME = "authorName";
    private final static String BOOK_AUTHOR_SURNAME = "authorSurname";
    private final static String BOOK_PUBLISHER = "publisher";
    private final static String BOOK_CITY_PUBLISHER = "cityPublisher";
    private final static String BOOK_YEAR_PUBLISHED = "yearPublished";
    private final static String BOOK_ISBN = "ISBN";
    private final static String BOOK_PRINT_RUN = "printRun";
    private final static String BOOK_PAPER_BACK = "paperBack";

    @Override
    public Book build(Map<String, String> dataFromRequest) throws ControllerUtilException {
        Book book = new Book(ControllerUtil.parseStringToIntFromMap(dataFromRequest, BOOK_ID),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, BOOK_NAME),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, BOOK_AUTHOR_NAME),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, BOOK_AUTHOR_SURNAME),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, BOOK_PUBLISHER),
                ControllerUtil.getValueFromMapByKey(dataFromRequest, BOOK_CITY_PUBLISHER),
                ControllerUtil.parseSQLDateFromMap(dataFromRequest, BOOK_YEAR_PUBLISHED),
                ControllerUtil.parseStringToIntFromMap(dataFromRequest, BOOK_ISBN),
                ControllerUtil.parseStringToIntFromMap(dataFromRequest, BOOK_PRINT_RUN),
                ControllerUtil.parseStringToIntFromMap(dataFromRequest, BOOK_PAPER_BACK));
        return book;
    }
}
