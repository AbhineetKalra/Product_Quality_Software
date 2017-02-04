package pqs_163;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creates and empty address book which can be used to create contacts with fields
 * name, phone number, postal address, email address and note.
 * Also has the functionality to save the address book to a file and read from a file
 * with appropriate formatting.
 * 
 * @author Omer Solmazer 
 * @version 1.0 
 *  
 */

public class AddressBook {
  
  private ArrayList<AddressBookEntry> entries;

  
  /**
   * Default constructor for the address book class.
   * Instantiates the list containing entries of the address book
   * 
   * */
  public AddressBook(){
    this.entries = new ArrayList<AddressBookEntry>();
  }
  
  /**
   * Remove an entry from the address book with given object
   * 
   * @param entry   The entry to be removed from the address book
   * 
   * @return          <code>true</code> if the entry is removed successfully;
   *                  <code>false</code> otherwise.
   * 
   * */
  
  public void removeEntry(AddressBookEntry entry){
    this.entries.remove(entry);
  }
  
  /**
   * Adds new contact to the address book if it does not contain only white spaces.
   * 
   * @param name          Name of the contact to be added.
   * @param phoneNumber   phone number of the contact to be added, can be null.
   * @param postalAddress Address of the contact to be added, can be null.
   * @param emailAdress   Email of the contact to be added, can be null.
   * @param note          Note to be added to the contact, can be null.
   * 
   * @return          <code>true</code> if the contact is added successfully;
   *                  <code>false</code> otherwise.
   * 
   * */
  
  public boolean addNewContact(String name, String phoneNumber, String postalAddress,
      String emailAddress, String note){
    
    if(name == null){
      return false;
    }
    
    if(name.replace(" \n\t", "").length() == 0){
      return false;
    }
    
    AddressBookEntry newEntry = new AddressBookEntry(name, phoneNumber, postalAddress,
            emailAddress, note);
    this.entries.add(newEntry);
    
    return true;
  }
  
  private void addNewContact(AddressBookEntry newEntry){
    this.entries.add(newEntry);
  }
  
  /**
   * Remove an entry from the address book with the given ID
   * 
   * @param entryID   The ID of the entry to be removed
   * 
   * @return          <code>true</code> if the entry is removed successfully;
   *                  <code>false</code> otherwise.
   * 
   * */
  
  public boolean removeEntry(Integer entryID){
    AddressBookEntry entryToBeRemoved = null;
    
    if(entryID == null){
      return false;
    }
    
    for(AddressBookEntry entry: this.entries){
      if(entry.getEntryID() == entryID){
        entryToBeRemoved = entry;
        break;
      }
    }
    
    if(entryToBeRemoved == null){
      return false;
    }
    else{
      this.entries.remove(entryToBeRemoved);
      return true;
    }
    
  }
  
  /**
   * Searches the entire address book by the given query.
   * Tries to find contacts which contain the exact given query,
   * partial matches are ignored.
   * 
   * @param query String to be searched for in the address book
   * 
   * @return an array list of entries that contains the given query 
   * 
   * */
  
  public ArrayList<AddressBookEntry> searchAddressBook(String query){
    ArrayList<AddressBookEntry> searchResults = new ArrayList<AddressBookEntry>();
    
    for(AddressBookEntry entry: this.entries){
      if(entry.containsString(query)){
        searchResults.add(entry);
      }
    }
    
    return searchResults;
  }
  
  
  /**
   * Saves the current address book to a file. If no file is found with the given path,
   * a new file is created. If there exists a file with the same name, it is overwritten. 
   * Conversion from book entry list to appropriate format is handled here. 
   * 
   * @param pathToFile   The path to save the file at
   * 
   * @throws IOException If an error is occurred during writing into the file,
   *                     an exception is thrown
   * 
   * */
  
  public void saveToFile(String pathToFile) throws IOException{
    FileWriter newFile = new FileWriter(pathToFile);
    for(AddressBookEntry currentEntry: this.entries){
      
      String nameToFileFormat = convertFieldToFileLine(currentEntry.getName());
      newFile.write(nameToFileFormat);
      
      String phoneNumberToFileFormat = convertFieldToFileLine(currentEntry.getPhoneNumber());
      newFile.write(phoneNumberToFileFormat);
      
      String postalAddressToFileFormat = convertFieldToFileLine(currentEntry.getPostalAddress());
      newFile.write(postalAddressToFileFormat);
      
      String emailAddressToFileFormat = convertFieldToFileLine(currentEntry.getEmailAddress());
      newFile.write(emailAddressToFileFormat);
      
      String noteToFileFormat = convertFieldToFileLine(currentEntry.getNote());
      newFile.write(noteToFileFormat);
      
      newFile.flush();
    }
    
    newFile.close();
    
  }
  
  /**
   * Reads from a file with appropriate formatting and handles conversion to
   * take into account new line characters in fields. 
   *  
   * @param pathToFile absolute or the relative address of the file. If file is not
   * found, an IOException will be thrown.
   * 
   * @return a new address book that contains the address book entries from the file
   * 
   * @throws IOException thrown if an error occurs during reading the file
   * @throws StringIndexOutOfBoundsException thrown if an error occurs during reading data
   * from the file. Most likely to occur if the address book is changed without using
   * saveToFile method
   * 
   * 
   * */
  
  public AddressBook readFromFile(String pathToFile) throws IOException, 
      StringIndexOutOfBoundsException{
    AddressBook addressBookFromFile = new AddressBook();
    FileReader fileReader = new FileReader(pathToFile);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    String currentLine = bufferedReader.readLine();

    while(currentLine != null){
      
      String nameField = currentLine;
      String nameIndexLine = bufferedReader.readLine();
      String name = convertFileEntryToString(nameField, nameIndexLine);
      AddressBookEntry currentEntry = new AddressBookEntry(name, "","","","");
      
      String phoneField = bufferedReader.readLine();
      String phoneIndexLine = bufferedReader.readLine();
      String phoneNumber = convertFileEntryToString(phoneField, phoneIndexLine);      
      currentEntry.setPhoneNumber(phoneNumber);
      
      String postalAddressField = bufferedReader.readLine();
      String postalAddressIndexLine = bufferedReader.readLine();
      String postalAddress = convertFileEntryToString(postalAddressField, postalAddressIndexLine);
      currentEntry.setPostalAddress(postalAddress);
      
      String emailAddressField = bufferedReader.readLine();
      String emailAddressIndexLine = bufferedReader.readLine();
      String emailAddress = convertFileEntryToString(emailAddressField, emailAddressIndexLine);
      currentEntry.setEmailAddress(emailAddress);
      
      String noteField = bufferedReader.readLine();
      String noteIndexLine = bufferedReader.readLine();
      String note = convertFileEntryToString(noteField, noteIndexLine);
      currentEntry.setNote(note);
        
      addressBookFromFile.addNewContact(currentEntry);
      currentLine = bufferedReader.readLine();
    }
    
    bufferedReader.close();
    
    return addressBookFromFile;
  }
  
  private String convertFileEntryToString(String fieldLine, String indicesLine){
    String[] indices = indicesLine.split(" ");
    StringBuilder resultingString = new StringBuilder();
    String newLineAppended = fieldLine;
    
    for(String index: indices){
      try{
        newLineAppended = appendToIndex(Integer.parseInt(index), newLineAppended);
      }
      catch(NumberFormatException exceptionToIgnore){
        
      }
    }
    
    resultingString.append(newLineAppended);
    
    if(resultingString.length() == 0){
      resultingString.append(fieldLine);
    }
    
    return resultingString.toString();
  }
  
  private String convertFieldToFileLine(String field){
    if(field == null){
      return "\n\n";
    }
    
    StringBuilder resultingLine = new StringBuilder();
    ArrayList<Integer> newLineIndices = new ArrayList<Integer>();
    
    while(field.contains("\n")){
      newLineIndices.add(field.indexOf("\n"));
      field = field.replaceFirst("\n", "");
    }
    
    Collections.sort(newLineIndices, Collections.reverseOrder());
    
    resultingLine.append(field);
    resultingLine.append("\n");
    
    for(int i: newLineIndices){
      resultingLine.append(i + " ");
    }
    
    resultingLine.append("\n");
    return resultingLine.toString();
  }
  
  private String appendToIndex(int index, String toModify){
    String prefix = toModify.substring(0, index);
    String suffix = toModify.substring(index, toModify.length());
    return prefix + "\n" + suffix;
  }
  
}
