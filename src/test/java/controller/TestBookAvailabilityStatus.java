package controller;

import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestBookAvailabilityStatus {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForBookAvailabilityStatus () {
        return new Object[][]{
                {"action=BOOK_AVAILABILITY_STATUS&bookId=3&isAvailable=false&id=3","Status was changed"},
                {"action=BOOK_AVAILABILITY_STATUS&bookId=4&isAvailable=false&id=4","Status was changed"},
                {"action=BOOK_AVAILABILITY_STATUS&bookId=3&isAvailable=true&id=3","Status was changed"},
                {"action=BOOK_AVAILABILITY_STATUS&bookId=4&isAvailable=true&id=4","Status was changed"},




        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForBookAvailabilityStatus () {
        return new Object[][]{
                {"action=BOOK_AVAILABILITY_STATUS&bookId=3&isAvailable=null&id=3","Error during changing books status"},
                {"action=BOOK_AVAILABILITY_STATUS&bookId=3&isAvailable=Ashsg&id=3","Error during changing books status"},
                {"action=BOOK_AVAILABILITY_STATUS&bookId=3&isAvailable=null&id=12","Access denied"},
                {"action=BOOK_AVAILABILITY_STATUS","Access denied"},
                {"action=BOOK_AVAILABILITY_STATUS&bookId=-3&isAvailable=false&id=3","request doesn't content key status or bookId"},
        };
    }

    @Test(dataProvider = "setUpPositiveDataForBookAvailabilityStatus")
    public void testPositiveBookAvailabilityStatus (String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForBookAvailabilityStatus")
    public void testNegativeBookAvailabilityStatus (String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
