package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestRegistrateCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForUser() {
        return new Object[][]{
                {"action=USER_REGISTRATION&login=monk4&password=monk4&userName=Anto&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666","User was added"},
                {"action=USER_REGISTRATION&login=monk5&password=monk5","User was added"},


        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForUser() {
        return new Object[][]{
                {"action=USER_REGISTRATION&login=ilashka&password=ilashka","Error during registration procedure"},
                {"action=USER_REGISTRATION&login=ilashka&password=ilashka&email=anto.anto","Error during registration procedure"},
                {"action=USER_REGISTRATION&login=ilashka&password=ilashka&birthday=03-02-1999","Error during registration procedure"},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForUser")
    public void testPositiveRegistrationCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForUser")
    public void testNegativeRegistrationCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
