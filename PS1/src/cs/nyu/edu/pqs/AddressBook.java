package cs.nyu.edu.pqs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class stores and maintains Address books of type <tt>{@link Entry}</tt>.
 * <p>
 * The <tt>AddressBook</tt> does not stores duplicate <tt>Entry</tt>. It provides methods to add an
 * <tt>Entry</tt>, delete an <tt>Entry</tt>, search an <tt>Entry</tt>, save <tt>AddressBook</tt> to
 * a file, and load <tt>AddressBook</tt> from a file.
 * </p>
 * 
 * @author Abhineet Kalra
 * @version 1.0
 */
public class AddressBook {

  private ArrayList <Entry> phoneBook;

  private AddressBook() {
    phoneBook = new ArrayList <Entry>();
  }

  /**
   * Creates ArrayList of <tt>Entry</tt> type.
   * 
   * @return an empty addressBook.
   */
  public static AddressBook create(){
    return new AddressBook();
  }

  /**
   * Appends a new element of type <tt>Entry</tt> to phoneBook in sorted order.
   * <p>
   * Addition will fail if <tt>Entry</tt> is null or duplicate.
   * </p>
   * 
   * @param newEntry
   *          The <tt>Entry</tt> to be added.
   * @return true if the appending was successful else false.
   */
  public boolean addEntry(Entry newEntry){
    if (!phoneBook.contains(newEntry) && newEntry != null) {
      this.phoneBook.add(newEntry);
      Collections.sort(phoneBook);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Deletes the entry if it is present in the addressBook.
   * <p>
   * Deletion will be failed if Entry is <i>null</i> or if the <tt>Entry</tt> is not present in
   * phoneBook.
   * </p>
   * 
   * @param unwantedEntry
   *          The <tt>Entry</tt> to be deleted.
   * @return true if the deletion of item was successful else false.
   */
  public boolean deleteEntry(Entry unwantedEntry){
    if (unwantedEntry != null && phoneBook.contains(unwantedEntry)) {
      return this.phoneBook.remove(unwantedEntry);
    } else {
      return false;
    }
  }

  /**
   * Searches for <tt>Entries<tt> containing the queried string.
   * <p>
   * This search method is case-insensitive. It searches all the attributes of each <tt>Entry</tt>
   * present in phoneBook for the queryString.
   * </p>
   * 
   * @param queryString
   *          Text to be searched for. It cannot be null or empty.
   * @return List of <tt>Entries</tt> containing the queryString.
   */
  public String searchEntry(String queryString){
    ArrayList <Entry> searchResults = new ArrayList<>();
    if (! (queryString.isEmpty()) && queryString != null) {
      for (Entry phonebookItem : phoneBook) {
        if (phonebookItem.toString().toLowerCase().contains(queryString.toLowerCase())) {
          searchResults.add(phonebookItem);
        }
      }
    }
    if (searchResults.size() > 0) {
      return searchResults.toString();
    } else {
      return "String Not Found";
    }
  }

  /**
   * Saves phoneBook to the specified path in JSON format. The path of file should be relative.
   * 
   * @param filePath
   *          It can not be null or empty. filePath is also case-sensitive.
   * @throws IOException
   *           When file is no longer accessible.
   * @throws IllegalArgumentException
   *           When filePath is not valid.
   */
  public void savePhoneBook(String filePath){
    JSONArray jsonObjectArray = new JSONArray();
    for (Entry saveEntry : this.phoneBook) {
      JSONObject entryToJsonObject = new JSONObject();
      entryToJsonObject.put("Name", saveEntry.getName());
      entryToJsonObject.put("PhoneNumber", saveEntry.getPhoneNumber());
      entryToJsonObject.put("PostalAddress", saveEntry.getPostalAddress());
      entryToJsonObject.put("Email", saveEntry.getEmailAddress());
      entryToJsonObject.put("Note", saveEntry.getNote());
      jsonObjectArray.add(entryToJsonObject);
    }
    if ( (filePath != null) && (!filePath.isEmpty())) {
      PrintWriter writer = null;
      try {
        String relativePath = new File(".").getCanonicalPath() + "//" + filePath;
        writer = new PrintWriter(
            new OutputStreamWriter(new FileOutputStream(relativePath), "utf-8"));
        writer.flush();
        writer.write(jsonObjectArray.toJSONString());
        writer.close();
      } catch (IOException e) {
        throw new IllegalArgumentException("File not found");
      } finally {
        if (writer != null)
          writer.close();
      }
    } else {
      throw new IllegalArgumentException("Not a valid filePath");
    }
  }

  /**
   * Loads the phoneBook form an existing JSON file. The path of the file should be relative.
   * 
   * @param filePath
   *          It can not be null or empty. filePath is also case-sensitive.
   * @return a new phoneBook containing Entries loaded from file.
   * @throws IOException
   *           When file is no longer accessible.
   * @throws FileNotFoundException
   *           When file is not found at the destination.
   * @throws ParseException
   *           When error occurs in the JSON text.
   */
  public AddressBook loadAddressBook(String filePath){
    JSONParser parser = new JSONParser();
    JSONArray jsonPhoneBookArray = null;
    try {
      String relativePath = new File(".").getCanonicalPath() + "//" + filePath;
      jsonPhoneBookArray = (JSONArray) parser.parse(new FileReader(relativePath));
    } catch (ParseException e) {
      throw new IllegalArgumentException("File not in correct json format");
    } catch (IOException e) {
      throw new IllegalArgumentException("File not found");
    }
    for (Object arrayElement : jsonPhoneBookArray) {
      JSONObject jsonEntry = (JSONObject) arrayElement;
      String name = (String) jsonEntry.get("Name");
      String phoneNumber = (String) jsonEntry.get("PhoneNumber");
      String postalAddress = (String) jsonEntry.get("PostalAddress");
      String emailAddress = (String) jsonEntry.get("Email");
      String note = (String) jsonEntry.get("Note");
      Entry.Builder entryToAdd = new Entry.Builder(name, phoneNumber);
      if (postalAddress == null) {
        entryToAdd.postalAddress(null);
      } else if (!postalAddress.equals("")) {
        entryToAdd.postalAddress(postalAddress);
      }
      if (emailAddress == null) {
        entryToAdd.emailAddress(null);
      } else if (!emailAddress.equals("")) {
        entryToAdd.emailAddress(emailAddress);
      }
      if (note == null) {
        entryToAdd.note(null);
      } else if (!note.equals("")) {
        entryToAdd.note(note);
      }
      this.addEntry(entryToAdd.build());
    }
    return this;
  }

  // Overridden toString method for better readability of the addressBook.
  @Override
  public String toString(){
    int index = 1;
    String result = "";
    for (Entry item : this.phoneBook) {
      result = result + index + ". " + item.toString() + "\n";
      index++;
    }
    return result;
  }
}