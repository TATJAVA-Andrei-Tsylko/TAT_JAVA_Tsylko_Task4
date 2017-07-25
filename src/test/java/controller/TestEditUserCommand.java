package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestEditUserCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForEditUserCommand() {
        return new Object[][]{
                {"action=USER_EDITED&userId=14&password=monkM&userName=Anto&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666&id=10","User was edited"},
                {"action=USER_EDITED&userId=14&password=monk5&userName=Azazel&userSurname=Anto&birthday=1999-02-03&phone=37525515666&id=3","User was edited"},
                {"action=USER_EDITED&userId=14&userName=Jack&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666&id=4","User was edited"},
                {"action=USER_EDITED&userId=14&userSurname=Anto&email=anto@mail.ru&phone=37525515666&id=4","User was edited"},


        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForEditUserCommand() {
        return new Object[][]{
                {"action=USER_EDITED&userId=14&password=monkM&userName=Anto&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666","Access denied"},
                {"action=EDIT_ADDRESS","Access denied"},
                {"action=USER_EDITED&userName=Jack&userSurname=Anto&birthday=1986-02-03&email=anto@mail.ru&phone=37525515666&id=4","Error during edit procedure"},
                {"action=USER_EDITED&userName=Jack&userSurname=Anto&birthday=1986-02-03&email=ZZZZZZZ&phone=37525515666&id=4","Error during edit procedure"},
                {"action=USER_EDITED&userName=Jack&userSurname=Anto&birthday=02-02-1999&email=anto@mail&phone=37525515666&id=4","Error during edit procedure"},
                {"action=USER_EDITED&userName=Jack&userSurname=Anto&birthday=1986-02-03&email=anto@mail&phone&phone=37525515666&id=13","Access denied"},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForEditUserCommand")
    public void testPositiveEditUserCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForEditUserCommand")
    public void testNegativeEditUserCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
