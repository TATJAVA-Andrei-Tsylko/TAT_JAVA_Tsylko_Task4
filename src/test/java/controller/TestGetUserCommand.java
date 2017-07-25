package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestGetUserCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForGetUserCommand() {
        return new Object[][]{
                {"action=GET_USER&userId=10&id=10","User{id=10, login='monk2', password='monkM', status=true, userName='Anto', userSurname='Anto', birthday=1986-02-03, address=Address{id=0, country='null', city='null', street='null', house=0, room=0}, email='anto@mail.ru', phone='37525515666', role=USER}"},



        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForGetUserCommand() {
        return new Object[][]{
                {"action=GET_USER&id=10","User was not found."},
                {"action=GET_USER&userId=10","Access denied"},
                {"action=GET_USER&userId=-10&id=10","User was not found."},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForGetUserCommand")
    public void testPositiveGetUserCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForGetUserCommand")
    public void testNegativeGetUserCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
