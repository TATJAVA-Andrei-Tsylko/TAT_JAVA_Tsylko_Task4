package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestGetCurrentAddressCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForGetCurrentAddressCommand() {
        return new Object[][]{
                {"action=CURRENT_ADDRESS&addressId=4&id=2","Address{id=4, country='UK', city='Manchester', street='Green Street', house=20, room=201}"},
                {"action=CURRENT_ADDRESS&addressId=5&id=3","Address{id=5, country='German', city='Manchester', street='Green Street', house=20, room=201}"},
                {"action=CURRENT_ADDRESS&addressId=2&id=4","Address{id=2, country='German', city='Passau', street='Innstraße ', house=41, room=0}"},



        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForGetCurrentAddressCommand() {
        return new Object[][]{
                {"action=CURRENT_ADDRESS&addressId=4","Access denied"},
                {"action=CURRENT_ADDRE","Error"},
                {"action=CURRENT_ADDRESS&addressId=4&id=13","Access denied"},
                {"aktion=CURRENT_ADDRESS&addressId=4&id=13","Invalid link"}
        };
    }

    @Test(dataProvider = "setUpPositiveDataForGetCurrentAddressCommand")
    public void testPositiveGetCurrentAddressCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForGetCurrentAddressCommand")
    public void testNegativeGetCurrentAddressCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
