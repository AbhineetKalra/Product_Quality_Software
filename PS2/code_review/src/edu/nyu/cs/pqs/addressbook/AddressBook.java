package edu.nyu.cs.pqs.addressbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public final class AddressBook {
  private List<AddressBookEntry> entryList;

  /**
   * Create a new AddressBook object.
   */
  public AddressBook() {
    entryList = new ArrayList<>();
  }

  /**
   * Search a given value by given AddressBookField.
   * 
   * @param value The value used to search for the entry.
   * @param field The field to be searched by.
   * @return A list of AddressBookEntry objects with the given value in the given field.
   */
  public List<AddressBookEntry> search(String value, AddressBookField field) {
    List<AddressBookEntry> res = new ArrayList<>();
    for (AddressBookEntry entry : entryList) {
      switch (field) {
        case NAME:
          if (entry.getName().contains(value)) {
            res.add(entry);
          }
          break;
        case POSTAL_ADDRESS:
          if (entry.getPostalAddress().contains(value)) {
            res.add(entry);
          }
          break;
        case PHONE_NUMBER:
          if (entry.getPhoneNumber().contains(value)) {
            res.add(entry);
          }
          break;
        case EMAIL_ADDRESS:
          if (entry.getEmailAddress().contains(value)) {
            res.add(entry);
          }
          break;
        case NOTE:
          if (entry.getNote().contains(value)) {
            res.add(entry);
          }
          break;
        default:
          break;
      }
    }
    return res;
  }

  /**
   * Add an AddressBookEntry object to the AddressBook object.
   * 
   * @param entry The AddressBookEntry object to be added.
   * @return True if it succeeds; false if it fails.
   */
  public boolean add(AddressBookEntry entry) {
    return entryList.add(entry);
  }

  /**
   * Remove an AddressBookEntry object from the AddressBook object.
   * 
   * @param entry The AddressBookEntry object to be removed.
   * @return True if it succeeds; false if it fails.
   */
  public boolean remove(AddressBookEntry entry) {
    return entryList.remove(entry);
  }

  /**
   * Serialize the object, then write the serialized object to the given file.
   * 
   * @param filePath The path of the file to write the serialized object.
   * @throws IOException If there is an IOException when open the file.
   */
  public void writeObject(String filePath) throws IOException {
    String serializedAddressBook = serialize();
    FileUtils.writeStringToFile(new File(filePath), serializedAddressBook, "UTF-8");
  }

  /**
   * Deserialize an AddressBook object from the given file.
   * 
   * @param filePath The path of the file to read the serialized object.
   * @return An AddressBook object deserialized from the given file
   * @throws IOException If there is an IOException when open the file.
   */
  public static AddressBook readObject(String filePath) throws IOException {
    String serializedAddressBook = FileUtils.readFileToString(new File(filePath), "UTF-8");
    return deserialize(serializedAddressBook);
  }

  /**
   * Serialize the AddressBook object to string.
   * 
   * @return The serialized string of current AddressBook object.
   */
  public String serialize() {
    StringBuffer sb = new StringBuffer();
    for (AddressBookEntry entry : entryList) {
      String serializedEntry = entry.serialize();
      sb.append(serializedEntry.length()).append('/').append(serializedEntry);
    }
    return sb.toString();
  }

  /**
   * Deserialize an AddressBook object from a string.
   * 
   * @param input The string to be deserialized.
   * @return An AddressBook object.
   */
  public static AddressBook deserialize(String input) {
    AddressBook addressBook = new AddressBook();
    int i = 0;
    while (i < input.length()) {
      int slash = input.indexOf('/', i);
      int size = Integer.valueOf(input.substring(i, slash));
      String serializedEntry = input.substring(slash + 1, slash + size + 1);
      AddressBookEntry entry = AddressBookEntry.deserialize(serializedEntry);
      addressBook.add(entry);
      i = slash + size + 1;
    }
    return addressBook;
  }
}
