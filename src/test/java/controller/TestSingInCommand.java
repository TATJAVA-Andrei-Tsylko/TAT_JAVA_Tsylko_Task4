package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestSingInCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForSignInCommand() {

        return new Object[][]{
                {"action=SIGN_IN&login=monk3&password=monk3","User was signed in"},
                {"action=SIGN_IN&login=monk5&password=monk5","User was signed in"},


        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForSignInCommand() {
        return new Object[][]{

                {"action=SIGN_IN&login=monk4&password=monk4","Error during sign in operation."},
                {"action=SIGN_IN","Invalid link"},
                {"action=SIGN_IN&login=monk3&password=monk4","Error during sign in operation."},
                {"action=SIGN_IN&login=monk3&","Error during sign in operation."},
                {"action=SIGN_IN&login=","Error during retrieving user"},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForSignInCommand")
    public void testPositiveSignInCommandCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForSignInCommand")
    public void testNegativeSignInCommandCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
