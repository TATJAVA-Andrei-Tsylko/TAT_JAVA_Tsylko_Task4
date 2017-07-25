package controller.xml;


import com.epam.tsylko.andrei.controller.XMLController;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestXmlDomController {
    private final static String DOM_PARSER = "DOM";
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
        controller.setParser(DOM_PARSER);
        Assert.assertEquals(controller.executeTask(request),response);

    }

}
