package pqs_163;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class AddressBookTest {

  @Test
  public void testAddNewContact() {
    AddressBook testBook = new AddressBook();
    assertTrue(testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote"));
    assertEquals(1, testBook.searchAddressBook("TestName").size());
  }

  @Test
  public void testAddNewContact_addDuplicateContact() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    assertTrue(testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote"));
    assertEquals(2, testBook.searchAddressBook("TestName").size());
  }

  @Test
  public void testAddNewContact_addEmptyNameContact() {
    AddressBook testBook = new AddressBook();
    assertFalse(testBook.addNewContact("", "123", "USA", "abc@test.com", "TestNote"));
    assertEquals(0, testBook.searchAddressBook("USA").size());

  }

  @Test
  public void testAddNewContact_addInvalidContact() {
    AddressBook testBook = new AddressBook();
    assertFalse(testBook.addNewContact(null, "123", "USA", "abc@test.com", "TestNote"));
    assertEquals(0, testBook.searchAddressBook("USA").size());
  }

  /*
   * Not mentioned in JavaDoc whether empty string value is allowed for name or not. Assuming it is
   * not allowed.
   */
  @Test
  public void testAddNewContact_addEmptyName() {
    AddressBook testBook = new AddressBook();
    assertFalse(testBook.addNewContact("", "123", "USA", "abc@test.com", "TestNote"));
    assertEquals(0, testBook.searchAddressBook("USA").size());
  }

  /*
   * According to JavaDoc the return type of removeEntry should be boolean and not void. Commenting
   * line 70 as there is no assertTrue() for void methods(Was throwing an error). Testing it the
   * alternate way.
   */
  @Test
  public void testRemoveEntry_passingParameterAddressBookEntry() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.addNewContact("XYZ", "000", "India", "xyz@test.com", "sometext");
    AddressBookEntry resultEntry = testBook.searchAddressBook("TestName").get(0);
    // assertTrue(testBook.removeEntry(resultEntry));
    testBook.removeEntry(resultEntry);
    assertEquals(0, testBook.searchAddressBook("TestName").size());
    assertEquals(1, testBook.searchAddressBook("India").size());
    assertEquals(1, testBook.searchAddressBook("").size());

  }

  /*
   * According to JavaDoc the return type of removeEntry should be boolean and not void. Commenting
   * line 90 as there is no assertFalse() for void methods(Was throwing an error). Testing it the
   * alternate way.
   */
  @Test
  public void testRemoveEntry_passingParameterAddressBookEntry_EntryNotPresent() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.addNewContact("XYZ", "000", "India", "xyz@test.com", "sometext");
    AddressBookEntry testEntry = new AddressBookEntry("Abhineet", "999", "NY", "abc@test.com",
        "testtext");
    // assertFalse(testBook.removeEntry(testEntry));
    testBook.removeEntry(testEntry);
    assertEquals(2, testBook.searchAddressBook("").size());
  }

  @Test
  public void testRemoveEntry_passingParameterAddressBookEntry_NullEntry() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    // assertFalse(testBook.removeEntry(null));
    /*
     * Casting the null to Integer as there is compilation error due to same method names. Should
     * not create methods with same name.
     */
    testBook.removeEntry((AddressBookEntry) null);
    assertEquals(1, testBook.searchAddressBook("").size());
  }

  @Test
  public void testRemoveEntry_byEntryID_ifPresent() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    AddressBookEntry resultEntry = testBook.searchAddressBook("TestName").get(0);
    assertTrue(testBook.removeEntry(resultEntry.getEntryID()));
    assertEquals(0, testBook.searchAddressBook("").size());
  }

  @Test
  public void testRemoveEntry_byEntryID_notPresent() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    assertFalse(testBook.removeEntry(-1));
  }

  @Test
  public void testRemoveEntry_byEntryID_emptyAddressBook() {
    AddressBook testBook = new AddressBook();
    assertFalse(testBook.removeEntry(1));
  }

  @Test
  public void testRemoveEntry_byNullEntryID() {
    AddressBook testBook = new AddressBook();
    /*
     * Casting the null to Integer as there is compilation error due to same method names. Should
     * not create methods with same name.
     */
    assertFalse(testBook.removeEntry((Integer) null));
  }

  @Test
  public void testSearchAddressBook() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.addNewContact("XYZ", "000", "USA", "xyz@test.com", "Test");
    assertEquals(1, testBook.searchAddressBook("TestName").size());
    assertTrue(testBook.searchAddressBook("TestName").get(0).containsString("123"));
  }

  @Test
  public void testSearchAddressBook_notPresent() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    assertTrue(testBook.searchAddressBook("abhineet").isEmpty());
  }

  @Test
  public void testSearchAddressBook_emptyAddressBook() {
    AddressBook testBook = new AddressBook();
    assertEquals(0, testBook.searchAddressBook("").size());
  }

  @Test
  public void testSearchAddressBook_withWhitespaces() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    assertEquals(0, testBook.searchAddressBook(" TestName ").size());
  }

  /*
   * Should have returned false as the query is partial and the java docs mention the query should
   * be exact.
   */
  @Test
  public void testSearchAddressBook_partialString_querySubstringOfField() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    assertTrue(testBook.searchAddressBook("US").isEmpty());
  }

  @Test
  public void testSearchAddressBook_partialString_feildSubstringOfQuery() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    assertTrue(testBook.searchAddressBook("USAA").isEmpty());
  }

  /*
   * Not mentioned in the java docs whether the search is case sensitive or not. Assuming it to be
   * case sensitive since java docs mention the query should be exact.
   */
  @Test
  public void testSearchAddressBook_caseSensitiveQuery() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    assertTrue(testBook.searchAddressBook("usa").isEmpty());
  }

  /* Ideally the method should have thrown IllegalArgumentException. */
  @Test
  public void testSearchAddressBook_nullQuery() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", null);
    assertTrue(testBook.searchAddressBook(null).isEmpty());
  }

  @Test
  public void testSearchAddressBook_emptyQuery() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.addNewContact("XYZ", "000", "ABC", "xyz@test.com", "Test");
    assertEquals(2, testBook.searchAddressBook("").size());
  }

  @Test
  public void testSearchAddressBook_multipleResults_allFullMatches() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.addNewContact("XYZ", "000", "USA", "xyz@test.com", "Test");
    testBook.addNewContact("TestName", "987", "India", "123@test.com", "TestSecond");
    assertEquals(2, testBook.searchAddressBook("TestName").size());
    assertEquals("USA", testBook.searchAddressBook("TestName").get(0).getPostalAddress());
    assertEquals("India", testBook.searchAddressBook("TestName").get(1).getPostalAddress());
  }

  /* Failure, as the partial matches are not ignored */
  @Test
  public void testSearchAddressBook_multipleResults_somePartialMatch() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.addNewContact("XYZ", "000", "USA", "xyz@test.com", "Test");
    testBook.addNewContact("TestName", "987", "USAA", "123@test.com", "TestSecond");
    assertEquals(1, testBook.searchAddressBook("USA").size());
    assertEquals("123", testBook.searchAddressBook("USA").get(0).getPostalAddress());
  }

  @Test
  public void testSearchAddressBook_queryWithWhitespace() {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "Test\nNote");
    assertEquals(1, testBook.searchAddressBook("Test\nNote").size());
  }

  @Test
  public void testSaveToFile() throws IOException {
    AddressBook testBook = new AddressBook();
    File saveFile = new File("TestFile");
    saveFile.delete();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.addNewContact("TestName", "123", "India", "abc@test.com", "TestNote");
    testBook.saveToFile("TestFile");
    String content = new String(Files.readAllBytes(Paths.get("TestFile")));
    assertTrue(saveFile.exists());
    assertTrue(content.contains("TestName"));
    assertTrue(content.contains("USA"));
    assertTrue(content.contains("123"));
    assertTrue(content.contains("TestNote"));
    assertTrue(content.contains("abc@test.com"));
    assertTrue(content.contains("India"));
  }

  // Should have thrown an illegalArgumentException
  @Test (expected = IllegalArgumentException.class)
  public void testSaveToFile_EmptyPath() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.saveToFile("");
  }

  @Test
  public void testSaveToFile_emptyAddressBook() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.saveToFile("TestFile");
  }

  @Test
  public void testReadFromFile_emptyAddressBook() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.saveToFile("TestFile");
    testBook.readFromFile("TestFile");
    assertEquals(0, testBook.searchAddressBook("").size());
  }

  @Test (expected = FileNotFoundException.class)
  public void testSaveToFile_invalidPath() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.saveToFile("src/srccc/File");
  }

  // Should have thrown an illegalArgumentException instead throwing NullPointerException meaning
  // that developer didn't handle null.
  @Test (expected = IllegalArgumentException.class)
  public void testSaveToFile_nullPath() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.saveToFile(null);
  }

  @Test (expected = FileNotFoundException.class)
  public void testSaveToFile_noFileName() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.saveToFile("src/");
  }

  @Test
  public void testReadFromFile() throws IOException {
    AddressBook testBook = new AddressBook();
    AddressBook loadedBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    String testBookContent = "";
    String loadedBookContent = "";
    for (AddressBookEntry entry : testBook.searchAddressBook("")) {
      testBookContent = testBookContent + entry.toString();
    }
    testBook.saveToFile("TestFile");
    loadedBook = loadedBook.readFromFile("TestFile");
    for (AddressBookEntry entry : loadedBook.searchAddressBook("")) {
      loadedBookContent = loadedBookContent + entry.toString();
    }
    assertEquals(testBookContent, loadedBookContent);

    /*
     * assertEquals(testBook.searchAddressBook("").get(0).getEntryID(),
     * loadedBook.searchAddressBook("").get(0).getEntryID()); ###Since equals method is not defined,
     * not really sure if developer wanted ID's of contacts to be same for saved and loaded
     * addressbooks.
     */
    assertEquals(testBook.searchAddressBook("").size(), loadedBook.searchAddressBook("").size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testReadFromFile_nullFilePath() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.readFromFile(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testReadFromFile_emptyFilePath() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.readFromFile("");
  }

  @Test (expected = NullPointerException.class)
  public void testReadFromFile_contentChangedAtEnd() throws IOException {
    AddressBook testBook = new AddressBook();
    AddressBook loadedBook = new AddressBook();
    testBook.addNewContact("TestName", "123", "USA", "abc@test.com", "TestNote");
    testBook.saveToFile("TestFile");
    Files.write(Paths.get("TestFile"), "the added Text".getBytes(), StandardOpenOption.APPEND);
    loadedBook = loadedBook.readFromFile("TestFile");
  }

  @Test (expected = StringIndexOutOfBoundsException.class)
  public void testReadFromFile_indicesMoreThanFieldLength() throws IOException {
    AddressBook loadedBook = new AddressBook();
    loadedBook.saveToFile("TestFile");
    String contentOfFile = "";
    contentOfFile += "TestName\n50\n123\n\nUSA\n\nabc@test.com\n\nTestNote\n\n";
    Files.write(Paths.get("TestFile"), contentOfFile.getBytes(), StandardOpenOption.WRITE);
    loadedBook = loadedBook.readFromFile("TestFile");
  }

  @Test
  public void testReadFromFile_stringIndices() throws IOException {
    AddressBook loadedBook = new AddressBook();
    loadedBook.saveToFile("TestFile");
    String contentOfFile = "";
    contentOfFile += "TestName\nabc\n123\n\nUSA\n\nabc@test.com\n\nTestNote\n\n";
    Files.write(Paths.get("TestFile"), contentOfFile.getBytes(), StandardOpenOption.WRITE);
    loadedBook = loadedBook.readFromFile("TestFile");
    assertEquals("TestName", loadedBook.searchAddressBook("").get(0).getName());
  }

  @Test
  public void testReadFromFile_addressEntriesWithSpecialCharecters() throws IOException {
    AddressBook testBook = new AddressBook();
    AddressBook loadedBook = new AddressBook();
    testBook.addNewContact("Test\n,.|Name", "123", "U\nSA", null, "Test\tNote\t");
    String testBookContent = "";
    String loadedBookContent = "";
    for (AddressBookEntry entry : testBook.searchAddressBook("")) {
      testBookContent = testBookContent + entry.toString();
    }
    testBook.saveToFile("TestFile");
    loadedBook = loadedBook.readFromFile("TestFile");
    for (AddressBookEntry entry : loadedBook.searchAddressBook("")) {
      loadedBookContent = loadedBookContent + entry.toString();
    }
    assertEquals(testBookContent, loadedBookContent);
    assertEquals(testBook.searchAddressBook("").size(), loadedBook.searchAddressBook("").size());
  }

  // Should have thrown an illegalArgumentException
  @Test (expected = FileNotFoundException.class)
  public void testReadFromFile_noFileName() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.readFromFile("src/");
  }

  // Should have thrown an illegalArgumentException
  @Test (expected = FileNotFoundException.class)
  public void testReadFromFile_invalidPath() throws IOException {
    AddressBook testBook = new AddressBook();
    testBook.saveToFile("src/src/File");
  }
}
