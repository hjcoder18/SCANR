package test;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;

import com.example.hunter.scanr.Bag;
import com.example.hunter.scanr.Book;
import com.example.hunter.scanr.Shelf;

/**
 * Created by Hunter on 2/22/2016.
 */
public class ExampleTest extends InstrumentationTestCase{
    // test the shelf
    int theshelfid = 123;
    Shelf shelf = new Shelf(theshelfid);
    int shelvesId = shelf.getShelfId();

    // test the bag
    int bagsId = 98920;
    Bag bag1 = new Bag(bagsId);
    int bag1Id = bag1.getBagId();


    //test the book
    int thebookId = 235129;
    Book book = new Book(thebookId);
    int booksId = book.getBookID();

    public void test() throws Exception {
        //test shelf:
        Shelf testShelf1 = new Shelf(00001);

        //test bag:
        Bag testBag1 = new Bag(5555);
        int iders = 123456;
        testBag1.setStudentID(iders);
        //check if student id is good...
        assertEquals(testBag1.getStudentID(), iders);

        String ricky = "rickRoll";
        testBag1.setStudentName(ricky);
        //check if bag's student name is good...
        assertEquals(testBag1.getStudentName(), ricky);

        //test books:
        Book testBook1 = new Book((int) Math.random());
        String authy = "swartz";
        String namey = "AWESOME BOOK!";
        testBook1.setBookAuthor(authy);
        testBook1.setBookName(namey);
        //check that names and authors are lining up...
        assertEquals(authy, testBook1.getBookAuthor());
        assertEquals(namey, testBook1.getBookName());


        //from before... make sure things are equal.
        assertEquals(theshelfid, shelvesId);
        assertEquals(bagsId, bag1Id);
        assertEquals(booksId, thebookId);


        //test the lists of stuff! JOYFUL!

        //first, we need a shelf...
        Shelf someUnitTestShelf = new Shelf((int)Math.random());

        //now we do the following 3 times...
        for (int reallyWishThisDidntTakeSoLong = 1; reallyWishThisDidntTakeSoLong < 4; reallyWishThisDidntTakeSoLong++) {
            //step 1... create a bag
            Bag sillyBagsTricksAreForKids = new Bag((int) Math.random());
            sillyBagsTricksAreForKids.setBagId((int)Math.random() + reallyWishThisDidntTakeSoLong);
            sillyBagsTricksAreForKids.setStudentID((int) Math.random());
            sillyBagsTricksAreForKids.setStudentName("madman" + reallyWishThisDidntTakeSoLong);

            //now add stuff to it...(10 stuff's in particular)
            for (int soDoneWithUnitTesting = 1; soDoneWithUnitTesting < 11; soDoneWithUnitTesting++) {

                //create a book "randomly"...
                Book tempBook = new Book((int) Math.random() + soDoneWithUnitTesting);
                tempBook.setBookAuthor("unitTestMan");
                tempBook.setBookName("this one is called: " + soDoneWithUnitTesting);
                //not sure i want the id...hmmm i'll grab it anyway...
                int booksId = tempBook.getBookID(); //<-- RIGHT THERE!!!

                //throw the book in the bag and shake it all up.
                sillyBagsTricksAreForKids.addBook(tempBook);
            }

            //at this point we have a bag with 10 books in it! lets find out if we're right...
            assert(sillyBagsTricksAreForKids.numberOfBooks() == 10);

            //lastly... throw that bag on the shelf!
            someUnitTestShelf.addBag(sillyBagsTricksAreForKids);
        }

        //at this point, we have 1 shelf (someUnitTestShelf) and it should have
        //3 bags on it so now we find out if we're right!

        assert(someUnitTestShelf.numberOfBags() == 3);
    }
}
