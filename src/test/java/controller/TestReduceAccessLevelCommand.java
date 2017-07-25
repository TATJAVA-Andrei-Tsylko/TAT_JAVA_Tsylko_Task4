package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//Run after TestChangeUserRoleCommand
public class TestReduceAccessLevelCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDateForReduceAccessLevelCommand() {
        return new Object[][]{
                {"action=REDUCE_ACCESS_LEVEL_COMMAND&userId=10&role=USER&id=4","Role was reduced"},
                {"action=REDUCE_ACCESS_LEVEL_COMMAND&userId=12&role=USER&id=4","Role was reduced"},


        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForReduceAccessLevelCommand() {
        return new Object[][]{
                {"action=REDUCE_ACCESS_LEVEL_COMMAND&userId=10&role=ADMIN&id=4","Error during reduce access procedure"},
                {"action=REDUCE_ACCESS_LEVEL_COMMAND","Access denied"},
                {"action=REDUCE_ACCESS_LEVEL_COMMAND&userId=12&role=USER&id=14","Access denied"},

        };
    }

    @Test(dataProvider = "setUpPositiveDateForReduceAccessLevelCommand")
    public void testPositiveReduceAccessLevelCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForReduceAccessLevelCommand")
    public void testNegativeReduceAccessLevelCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
