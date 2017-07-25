package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestGetAllBooksCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForGetAllBooksCommand() {
        return new Object[][]{
                {"action=SHOW_ALL_BOOKS&id=1","Library size 6"},
                {"action=SHOW_ALL_BOOKS","Library size 6"},




        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForGetAllBooksCommand() {
        return new Object[][]{
                {"action=","Invalid link"},
                {"action=SHOW_ALL_BOOK","Error"},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForGetAllBooksCommand")
    public void testPositiveGetAllBooksCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForGetAllBooksCommand")
    public void testNegativeGetAllBooksCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
