package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestAddAddressCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForAddress() {

        return new Object[][]{
                {"action=HOME_ADDRESS&country=UK&city=London&street=Green Street&house=20&room=201&id=2","Address was added"},
                {"action=HOME_ADDRESS&country=Germany&city=Passau&street=Innerstrasse&id=10","Address was added"},


        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForAddress() {
        return new Object[][]{
                {"action=HOME_ADDRESS&country=Spain&city=Madrid&street=Green Street&house=-20&id=2","Error during add address procedure"},
                {"action=HOME_ADDRESS&country=Poland&city=Warsaw&street=Green Street&house=20&room=-300&id=2","Error during add address procedure"},
                {"action=HOME_ADDRESS&country=UK&house=20&room=-300&id=2","Error during add address procedure"},
                {"action=HOME_ADDRESS&country=Austria&house=20&room=-300","Access denied"},
        };
    }

    @Test(dataProvider = "setUpPositiveDataForAddress")
    public void testPositiveAddAddressCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForAddress")
    public void testNegativeAddAddressCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
