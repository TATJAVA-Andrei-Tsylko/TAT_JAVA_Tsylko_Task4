package controller.xml;

import com.epam.tsylko.andrei.controller.XMLController;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestXmlStaxController {
    private final static String STAX_PARSER = "STAX";
    XMLController controller = new XMLController();
    @DataProvider
    public Object[][] setUpPositiveDataForSignInCommand() {

        return new Object[][]{
                {"action=SIGN_IN&login=monk3&password=monk3","User was signed in"},
                {"action=SIGN_IN&login=monk5&password=monk5","User was signed in"},


        };
    }

    @Test(dataProvider = "setUpPositiveDataForSignInCommand")
    public void testPositiveSignInCommandCommand(String request, String response) {
        controller.setParser(STAX_PARSER);
        Assert.assertEquals(controller.executeTask(request),response);

    }
}
