package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestReserveBookCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForReservation() {

        return new Object[][]{
                {"action=BOOK_RESERVATION&userId=11&bookId=3&id=3","Book was reserved"},
                {"action=BOOK_RESERVATION&userId=11&bookId=4&id=12","Book was reserved"},
                {"action=BOOK_RESERVATION&userId=11&bookId=6&id=12","Book was reserved"},


        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForReservation() {
        return new Object[][]{

                {"action=BOOK_RESERVATION&userId=11&bookId=5","Access denied"},
                {"action=BOOK_RESERVATION","Access denied"},
                {"action=BOOK_RESERVATION&userId=11&bookId=-5&id=3","Error during book reservation procedure"},
                {"action=BOOK_RESERVATION&userId=11&bookId=6&id=11","Access denied"},
        };
    }

    @Test(dataProvider = "setUpPositiveDataForReservation")
    public void testPositiveReservationCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForReservation")
    public void testNegativeReservationCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
