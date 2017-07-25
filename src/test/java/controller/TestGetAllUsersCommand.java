package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestGetAllUsersCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForGetAllUsersCommand() {
        return new Object[][]{
                {"action=ALL_USERS&email=anto@mail.ru&phone=37525515666&id=3","There are 9 users."},
                {"action=ALL_USERS&id=4","There are 9 users."},




        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForGetAllUsersCommand() {
        return new Object[][]{
                {"action=ALL_USERS&id=14","Access denied"},
                {"action=ALL_USER","Error"},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForGetAllUsersCommand")
    public void testPositiveGetAllUsersCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForGetAllUsersCommand")
    public void testNegativeGetAllUsersCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
