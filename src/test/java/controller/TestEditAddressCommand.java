package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestEditAddressCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForEditAddressCommand() {
        return new Object[][]{
                {"action=EDIT_ADDRESS&addressId=4&country=UK&city=Manchester&street=Green Street&house=20&room=201&id=2","Edited address was added"},
                {"action=EDIT_ADDRESS&addressId=5&country=German&city=Manchester&street=Green Street&house=20&room=201&id=3","Edited address was added"},
                {"action=EDIT_ADDRESS&addressId=6&country=Ukraine&city=Kiev&street=Green Street&house=20&room=201&id=4","Edited address was added"},



        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForEditAddressCommand() {
        return new Object[][]{
                {"action=EDIT_ADDRESS&addressId=4&country=UK&city=Manchester&street=Green Street&house=20&room=201","Access denied"},
                {"action=EDIT_ADDRESS&country=ZZZZZZZ&city=Manchester&street=Green Street&house=20&room=22&id=2","Incorrect request"},
                {"action=EDIT_ADDRESS&addressId=4&city=Manchester&street=Green Street&house=20&id=2","Error during edit address procedure"},
                {"EDIT_ADDRESS&addressId=4&city=Manchester&street=Green Street&house=20&room=id=2","Invalid link"},


        };
    }

    @Test(dataProvider = "setUpPositiveDataForEditAddressCommand")
    public void testPositiveEditAddressCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForEditAddressCommand")
    public void testNegativeEditAddressCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
