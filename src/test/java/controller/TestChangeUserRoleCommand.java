package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestChangeUserRoleCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDateForChangeUserRoleCommand() {
        return new Object[][]{
                {"action=USER_ROLE&userId=10&role=ADMIN&id=3","Role was changed"},
                {"action=USER_ROLE&userId=12&role=ADMIN&id=4","Role was changed"},


        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForChangeUserRoleCommand() {
        return new Object[][]{
                {"action=USER_ROLE&userId=11&role=ADMIN&id=14","Access denied"},
                {"action=USER_ROLE","Access denied"},
                {"action=USER_ROLE&userId=11&role=SUPER_ADMIN&id=3","Incorrect request"},

        };
    }

    @Test(dataProvider = "setUpPositiveDateForChangeUserRoleCommand")
    public void testPositiveChangeUserRoleCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForChangeUserRoleCommand")
    public void testNegativeChangeUserRoleCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
