package controller;

import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestGetLivedLibraryBookCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDateForGetLivedLibraryBookCommand() {
        return new Object[][]{
                {"action=BOOK_LEAVED_LIBRARY&orderId=5&id=3","Book lived the library"},
                {"action=BOOK_LEAVED_LIBRARY&orderId=5&id=4","Book lived the library"},

        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForGetLivedLibraryBookCommand() {
        return new Object[][]{
                {"action=BOOK_LEAVED_LIBRARY&orderId=5&id=2","Access denied"},
                {"action=BOOK_LEAVED_LIBR","Error"},
                {"action=BOOK_LEAVED_LIBRARY&orderId=5","Access denied"},
                {"action=BOOK_LEAVED_LIBRARY&id=3","Error during operation isBorrowingBook"},


        };
    }

    @Test(dataProvider = "setUpPositiveDateForGetLivedLibraryBookCommand")
    public void testPositiveGetLivedLibraryBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForGetLivedLibraryBookCommand")
    public void testNegativeGetLivedLibraryBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
