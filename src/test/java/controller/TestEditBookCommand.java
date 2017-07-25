package controller;


import com.epam.tsylko.andrei.controller.Controller;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestEditBookCommand {
    private Controller controller = new Controller();
    @DataProvider
    public Object[][] setUpPositiveDataForEditBookCommand() {
        return new Object[][]{
                {"action=EDIT_BOOK&bookId=39&booksName=C# Advanced&authorName=Steve&authorSurname=Horn&publisher=Oreilly&cityPublisher=London&ISBN=978067&yearPublished=2000-03-12&id=3","Book was edited"},
                {"action=EDIT_BOOK&bookId=40&booksName=Assembler&authorName=Steve&authorSurname=Horn&publisher=Oreilly&cityPublisher=London&ISBN=978067&yearPublished=2000-03-12&id=4","Book was edited"},



        };
    }
    @DataProvider
    public Object[][] setUpNegativeDataForEditBookCommand() {
        return new Object[][]{
                {"action=EDIT_BOOK&booksName=Scala&id=3","Error during edited book procedure"},
                {"action=EDIT_BOOK&booksName=Objective-C&ISBN=008067&id=3","Error during edited book procedure"},
                {"action=EDIT_BOOK&booksName=Scala&authorSurname=Adorno&ISBN=978089&id=2","Access denied"},
                {"action=EDIT_BOOK&booksName=Scala&authorSurname=Adorno&ISBN=979&id=3","Error during edited book procedure"},
                {"action=EDIT_BOOK&bookId=41&yearPublished=2000-03-12&id=4","Error during edited book procedure"},

        };
    }

    @Test(dataProvider = "setUpPositiveDataForEditBookCommand")
    public void testPositiveEditBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }

    @Test(dataProvider = "setUpNegativeDataForEditBookCommand")
    public void testNegativeEditBookCommand(String request, String response) {

        Assert.assertEquals(controller.executeTask(request),response);

    }
}
