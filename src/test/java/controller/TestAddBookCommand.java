package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestAddBookCommand {
    private Controller controller = new Controller();

    @DataProvider
    public Object[][] setUpPositiveDataForBook() {
        return new Object[][]{
                {"action=ADD_BOOK&booksName=C#&authorName=Steve&authorSurname=Horn&publisher=Oreilly&cityPublisher=London&ISBN=978067&yearPublished=2000-03-12&id=3", "Book was added"},
                {"action=ADD_BOOK&booksName=Scala&authorSurname=Adorno&ISBN=978089&id=3", "Book was added"},
                {"action=ADD_BOOK&booksName=C++&authorSurname=Adorno&ISBN=978089&id=4", "Book was added"},


        };
    }

    @DataProvider
    public Object[][] setUpNegativeDataForBook() {
        return new Object[][]{
                {"action=ADD_BOOK&booksName=Scala&id=3", "Error during add book procedure"},
                {"action=ADD_BOOK&booksName=Objective-C&ISBN=008067&id=3", "Error during add book procedure"},
                {"action=ADD_BOOK&booksName=Scala&authorSurname=Adorno&ISBN=978089&id=2", "Access denied"},
                {"action=ADD_BOOK", "Access denied"},
                {"action=ADD_BOOK&booksName=Scala", "Access denied"},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForBook")
    public void testPositiveAddBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request), response);

    }

    @Test(dataProvider = "setUpNegativeDataForBook")
    public void testNegativeAddBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request), response);

    }

}
