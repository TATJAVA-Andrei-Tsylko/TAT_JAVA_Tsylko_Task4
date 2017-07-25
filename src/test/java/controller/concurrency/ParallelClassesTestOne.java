package controller.concurrency;


import com.epam.tsylko.andrei.server.Server;
import com.epam.tsylko.andrei.server.Worker;
import org.junit.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParallelClassesTestOne {
    private final static int SLEEP = 5000;

    @DataProvider(name = "setUpPositiveDataForConcurrency")
    public Object[][] setUpPositiveDataForConcurrency() {

        return new Object[][]{
                {new String[]{"action=EDIT_BOOK&bookId=39&booksName=Java Advanced&authorName=Jack&authorSurname=Gorn&publisher=Oreilly&cityPublisher=London&ISBN=978067&yearPublished=2000-03-12&id=3",
                        "action=EDIT_BOOK&bookId=39&booksName=Scala Advanced&authorName=Andy&authorSurname=Forn&publisher=Packt&cityPublisher=Arsenal&ISBN=978067&yearPublished=2000-03-12&id=3",
                        "action=EDIT_BOOK&bookId=39&booksName=Objective-C Advanced&authorName=Monty&authorSurname=Born&publisher=Princeton&cityPublisher=Berlin&ISBN=978067&yearPublished=2000-03-12&id=3",
                        "action=CURRENT_ADDRESS&addressId=5&id=3",
                        "action=CURRENT_ADDRESS&addressId=2&id=4",
                        "action=GET_BOOK&bookId=3&id=3",
                        "action=GET_BOOK&bookId=4&id=2"
                },
                        new String[]{"Book was edited",
                                "Book was edited",
                                "Book was edited",
                                "Address{id=5, country='German', city='Manchester', street='Green Street', house=20, room=201}",
                                "Address{id=2, country='German', city='Passau', street='Innstraße ', house=41, room=0}",
                                "Book{id=3, booksName='Clean Code: A Handbook of Agile Software Craftsmanship', authorName='Robert', authorSurname='Martin', publisher='Prentice Hall', cityPublisher='null', yearPublished=2008-11-09, isbn=132350882, printRun=0, paperBack=464, availability=true, reservation=false}",
                                "Book{id=4, booksName='Design Patterns: Elements of Reusable Object-Oriented Software', authorName='Erich', authorSurname='Gamma', publisher='Addison-Wesley Professional', cityPublisher='null', yearPublished=1994-10-10, isbn=201633612, printRun=0, paperBack=395, availability=true, reservation=true}"
                        }},
        };
    }

    @Test(dataProvider = "setUpPositiveDataForConcurrency")
    public void testPositiveDataForConcurrency(String[] request, String[] response) {
        Server server = Server.getInstance();
        server.start();
        for (int i = 0; i < request.length; i++) {
            System.out.println("i: " + i);
            server.processRequest(request[i]);
        }

        for (int i = 0; i < request.length; i++) {
            Worker worker;
            worker = server.getRequestResponse();
            Assert.assertEquals("Test result",worker.getResponse(), response[i]);
        }

        server.stop();

    }
}
