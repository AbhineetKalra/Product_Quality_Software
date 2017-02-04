package pqs_163;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class AddressBookEntryTest {

  @BeforeClass
  public static void testAddressBookEntryGetID() {
    AddressBookEntry testEntry = new AddressBookEntry("FirstTest", null, null, null, null);
    assertEquals(Integer.valueOf("1"), testEntry.getEntryID());
  }

  @Test
  public void testSetEmailAddress() {
    AddressBookEntry testEntry = new AddressBookEntry("abhineet", null, null, null, null);
    testEntry.setEmailAddress("ak5345");
    assertEquals("ak5345", testEntry.getEmailAddress());
  }

  @Test
  public void testSetPostalAddress() {
    AddressBookEntry testEntry = new AddressBookEntry("abhineet", null, null, null, null);
    testEntry.setPostalAddress("USA");
    assertEquals("USA", testEntry.getPostalAddress());
  }

  @Test
  public void testSetName() {
    AddressBookEntry testEntry = new AddressBookEntry("kalra", null, null, null, null);
    testEntry.setName("abhineet");
    assertEquals("abhineet", testEntry.getName());
  }

  @Test
  public void testSetPhone() {
    AddressBookEntry testEntry = new AddressBookEntry("abhineet", null, null, null, null);
    testEntry.setPhoneNumber("000");
    assertEquals("000", testEntry.getPhoneNumber());
  }

  @Test
  public void testSetNote() {
    AddressBookEntry testEntry = new AddressBookEntry("abhineet", null, null, null, null);
    testEntry.setNote("testnote");
    assertEquals("testnote", testEntry.getNote());
  }

  @Test
  public void testSetters_Nullvalues() {
    AddressBookEntry testEntry = new AddressBookEntry("testname", "+123", "testaddress",
        "test@email.com", "testnote");
    testEntry.setName(null);
    testEntry.setPostalAddress(null);
    testEntry.setPhoneNumber(null);
    testEntry.setEmailAddress(null);
    testEntry.setNote(null);
    assertEquals("testname", testEntry.getName());
    assertEquals("+123", testEntry.getPhoneNumber());
    assertEquals("testaddress", testEntry.getPostalAddress());
    assertEquals("test@email.com", testEntry.getEmailAddress());
    assertEquals("testnote", testEntry.getNote());
  }

  @Test
  public void testGetName() {
    AddressBookEntry testEntry = new AddressBookEntry("abhineet", null, null, null, null);
    assertEquals("abhineet", testEntry.getName());
  }

  @Test
  public void testGetPhoneNumber() {
    AddressBookEntry testEntry = new AddressBookEntry("testname", "000", null, null, null);
    assertEquals("000", testEntry.getPhoneNumber());
  }

  @Test
  public void testGetPostalAddress() {
    AddressBookEntry testEntry = new AddressBookEntry("testname", null, "U.S.A", null, null);
    assertEquals("U.S.A", testEntry.getPostalAddress());
  }

  @Test
  public void testGetEmailAddress() {
    AddressBookEntry testEntry = new AddressBookEntry("testname", null, null, "ak5345@nyu.edu",
        null);
    assertEquals("ak5345@nyu.edu", testEntry.getEmailAddress());
  }

  @Test
  public void testGetNote() {
    AddressBookEntry testEntry = new AddressBookEntry("testname", null, null, null, "testnote");
    assertEquals("testnote", testEntry.getNote());
  }

  @Test
  public void testCaseSenesitivityOfEntryFields() {
    AddressBookEntry testEntry = new AddressBookEntry("TestName", "+123", "TestAddress",
        "TestEmail", "TestNote");
    assertEquals("TestName", testEntry.getName());
    assertEquals("+123", testEntry.getPhoneNumber());
    assertEquals("TestAddress", testEntry.getPostalAddress());
    assertEquals("TestEmail", testEntry.getEmailAddress());
    assertEquals("TestNote", testEntry.getNote());
  }

  @Test
  public void testAddressBookEntryConstructor() {
    AddressBookEntry testEntry = new AddressBookEntry("TestName", "+123", "TestAddress",
        "TestEmail", "TestNote");
    assertEquals("TestName", testEntry.getName());
    assertEquals("+123", testEntry.getPhoneNumber());
    assertEquals("TestAddress", testEntry.getPostalAddress());
    assertEquals("TestEmail", testEntry.getEmailAddress());
    assertEquals("TestNote", testEntry.getNote());
  }

  @Test
  public void testAddressBookEntryConstructor_nullValues() {
    AddressBookEntry testEntry = new AddressBookEntry("testname", null, null, null, null);
    assertEquals("testname", testEntry.getName());
    assertEquals("", testEntry.getPhoneNumber());
    assertEquals("", testEntry.getPostalAddress());
    assertEquals("", testEntry.getEmailAddress());
    assertEquals("", testEntry.getNote());
  }

  @Test
  public void testContainsString() {
    AddressBookEntry testEntry = new AddressBookEntry("testname", "123", "TestAddress",
        "abhineet@test.com", "TestNote");
    assertFalse(testEntry.containsString("z"));
    assertFalse(testEntry.containsString(null));
    assertTrue(testEntry.containsString("testname"));
    assertTrue(testEntry.containsString("123"));
    assertTrue(testEntry.containsString("TestAddress"));
    assertTrue(testEntry.containsString("abhineet@test.com"));
    assertTrue(testEntry.containsString("TestNote"));
    assertTrue(testEntry.containsString(""));
  }

  /*
   * This test case fails as the code is checking for the exact same pattern of replacement. Thus,
   * this replacement will work if it is passed " \n\t". This is wrong.
   */
  @Test
  public void testAddressBookEntry_setNameWithWhitespaces() {
    AddressBookEntry testEntry = new AddressBookEntry("Abhineet Kalra", "123", null, null, null);
    testEntry.setName(" \n\n\n");
    assertEquals("Abhineet Kalra", (testEntry.getName()));
  }

  @Test
  public void testAddressBookEntry_setNameWithWhitespaces_spaceNewlineTab() {
    AddressBookEntry testEntry = new AddressBookEntry("Abhineet Kalra", "123", null, null, null);
    testEntry.setName(" \n\t");
    assertEquals("Abhineet Kalra", (testEntry.getName()));
  }

  /*
   * The below test case fails.The java doc mention that name field can't be null, thus it should
   * throw an IllegalArgumentException
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNullName() {
    AddressBookEntry testEntry = new AddressBookEntry(null, "+123", "TestAddress", "TestEmail",
        "TestNote");
  }
}
