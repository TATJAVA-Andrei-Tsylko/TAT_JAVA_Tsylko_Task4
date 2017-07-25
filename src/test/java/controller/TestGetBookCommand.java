package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestGetBookCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForGetBookCommand() {
        return new Object[][]{
                {"action=GET_BOOK&bookId=3&id=3","Book{id=3, booksName='Clean Code: A Handbook of Agile Software Craftsmanship', authorName='Robert', authorSurname='Martin', publisher='Prentice Hall', cityPublisher='null', yearPublished=2008-11-09, isbn=132350882, printRun=0, paperBack=464, availability=true, reservation=false}"},
                {"action=GET_BOOK&bookId=4&id=2","Book{id=4, booksName='Design Patterns: Elements of Reusable Object-Oriented Software', authorName='Erich', authorSurname='Gamma', publisher='Addison-Wesley Professional', cityPublisher='null', yearPublished=1994-10-10, isbn=201633612, printRun=0, paperBack=395, availability=true, reservation=true}"},




        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForGetBookCommand() {
        return new Object[][]{
                {"action=GET_BOOK&bookId=3&id=5","Access denied"},
                {"action=GET_BOOK&bookId=3","Access denied"},
                {"action=GET_BOOK&id=3","Incorrect request"},
                {"action=GET_BO&bookId=3&id=3","Error"},
        };
    }

    @Test(dataProvider = "setUpPositiveDataForGetBookCommand")
    public void testPositiveGetBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForGetBookCommand")
    public void testNegativeGetBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
