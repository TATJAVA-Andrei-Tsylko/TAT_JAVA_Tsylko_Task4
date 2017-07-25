package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestReceiveBookFromUserCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForReceiveBookFromUserCommand() {
        return new Object[][]{
                {"action=BOOK_RETURNED_COMMAND&orderId=5&id=3","Book was returned by user"},
                {"action=BOOK_RETURNED_COMMAND&orderId=5&id=4","Book was returned by user"},




        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForReceiveBookFromUserCommand() {
        return new Object[][]{
                {"action=BOOK_RETURNED_COMMAND&orderId=5&bookId=4&id=2","Access denied"},
                {"action=BOOK_RETURNED_COMMAND&bookId=4&id=3","Error during operation book returning"},
                {"action=BOOK_RETURNED_COMMAND","Access denied"},
                {"aktionBOOK_RETURNED_COMMAND&orderId=5&bookId=4&id=3","Invalid link"}
        };
    }

    @Test(dataProvider = "setUpPositiveDataForReceiveBookFromUserCommand")
    public void testPositiveReceiveBookFromUserCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForReceiveBookFromUserCommand")
    public void testNegativeReceiveBookFromUserCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
