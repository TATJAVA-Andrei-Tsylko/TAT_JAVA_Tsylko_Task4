package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//after TestReserveBookCommand
public class TestCancelBookReservationCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDateForCancelBookReservationCommand() {
        return new Object[][]{
                {"action=CANCELLATION_BOOK_RESERVATION&orderId=6&bookId=4&id=10","Book reservation was canceled"},
                {"action=CANCELLATION_BOOK_RESERVATION&orderId=6&bookId=3&id=3","Book reservation was canceled"},
                {"action=CANCELLATION_BOOK_RESERVATION&orderId=6&bookId=6&id=4","Book reservation was canceled"},

        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForCancelBookReservationCommand() {
        return new Object[][]{
                {"action=CANCELLATION_BOOK_RESERVATION&orderId=6&bookId=4","Access denied"},
                {"action=CANCELLATION_BOOK_RESERVATION","Access denied"},
                {"action=CANCELLATION_BOOK_RESERVATION&orderId=6&bookId=4&id=0","Access denied"},

        };
    }

    @Test(dataProvider = "setUpPositiveDateForCancelBookReservationCommand")
    public void testPositiveCancelBookReservationCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForCancelBookReservationCommand")
    public void testNegativeCancelBookReservationCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
