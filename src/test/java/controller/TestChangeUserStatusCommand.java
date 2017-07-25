package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestChangeUserStatusCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForChangeUserStatusCommand() {
        return new Object[][]{
                {"action=USER_STATUS&userId=11&status=false&id=3","Status was changed"},
                {"action=USER_STATUS&userId=11&status=true&id=4","Status was changed"},



        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForChangeUserStatusCommand() {
        return new Object[][]{
                {"action=USER_STATUS&userId=11&status=false&id=2","Access denied"},
                {"action=USER_STATUS&userId=11&status=null&id=3","Incorrect request"},
                {"action=USER_STATUS&userId=11&id=4","Incorrect request"},
                {"action=USER_STATUS&userId=11&status=false","Access denied"},
                {"action=USER_STAS&userId=11&status=false&id=3","Error"},
        };
    }

    @Test(dataProvider = "setUpPositiveDataForChangeUserStatusCommand")
    public void testPositiveChangeUserStatusCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForChangeUserStatusCommand")
    public void testNegativeChangeUserStatusCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
