package controller.xml;


import com.epam.tsylko.andrei.controller.XMLController;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestXmlSaxController {
    private final static String SAX_PARSER = "SAX";
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
        controller.setParser(SAX_PARSER);
        Assert.assertEquals(controller.executeTask(request),response);

    }
}
