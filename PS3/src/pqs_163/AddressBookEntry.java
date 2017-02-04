package pqs_163;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Base class for the address book entry object. Contains fields for creating
 * a new entry and methods for manipulating (i.e. setting and getting) data
 * 
 * @author Omer Solmazer 
 * @version 1.0 
 *  
 */

public class AddressBookEntry {

  private String name;
  private String postalAddress;
  private String phoneNumber;
  private String emailAddress;
  private String note;
  private Integer entryID;
  private static AtomicInteger nextID = new AtomicInteger();
  
  /**
   * 
   * Default constructor for the address book entry class.
   * Parameters should be supplied in order
   * 
   * Only name parameter should be not null. All other parameters are permitted to be
   * null values. If null value is given for the name, it will return null. For
   * all other fields, it will default to empty string when supplied null value.
   * 
   * */
  
  public AddressBookEntry(String name, String phoneNumber, String postalAddress, 
    String emailAddress, String note){
    if(name != null){
      this.name = name;
      
      if(phoneNumber != null){
        this.phoneNumber = phoneNumber;
      }
      else{
        this.phoneNumber = "";
      }
      
      if(postalAddress != null){
        this.postalAddress = postalAddress;
      }
      else{
        this.postalAddress = "";
      }
      
      if(emailAddress != null){
        this.emailAddress = emailAddress;
      }
      else{
        this.emailAddress = "";
      }
      
      if(note != null){
        this.note = note;
      }
      else{
        this.note = "";
      }
      
      this.entryID = nextID.incrementAndGet();
    }
    else{
      return;
    }
    
  }
  
  /**
   * Searches entire entry for the existence of the query supplied.
   * Partial queries are not supported, i.e. entry should contain exact query
   * 
   * @param query The query supplied to be searched for. If null query is given,
   *              will return false.
   * 
   * @return      <code>true</code> if the entry contains the query;
   *              <code>false</code> otherwise.
   * 
   * */
  public boolean containsString(String query){
    
    if(query == null){
      return false;
    }
    
    if(this.name.contains(query)){
      return true;
    }
    
    if(this.postalAddress.contains(query)){
      return true;
    }
    
    if(this.phoneNumber.contains(query)){
      return true;
    }
    
    if(this.emailAddress.contains(query)){
      return true;
    }
    
    if(this.note.contains(query)){
      return true;
    }
    
    return false;
  }
  
  /**
   * Overridden toString method from the Object class. Concatenates fields back to back
   * and returns it as a new string.
   * 
   * @return string representation of the entry
   * 
   * */
  
  @Override
  public String toString(){
    return this.name + " "+ this.phoneNumber + " " + this.postalAddress + " " +
      this.emailAddress + " "+ this.note;
  }
  
  
  public String getName(){
    return this.name;
  }
  
  public Integer getEntryID(){
    return this.entryID;
  }
  
  public String getPostalAddress() {
    return postalAddress;
  }
  
  /**
   * Sets the postal address of the query unless it is null. 
   * If null, then leaves unchanged.
   * 
   * */

  public void setPostalAddress(String postalAddress) {
    if(postalAddress == null){
      return;
    }
    this.postalAddress = postalAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the phone number of the query unless it is null. 
   * If null, then leaves unchanged.
   * 
   * */
  
  public void setPhoneNumber(String phoneNumber) {
    if(phoneNumber == null){
      return;
    }
    
    this.phoneNumber = phoneNumber;
  }

  public String getEmailAddress() {
    return emailAddress;
  }
  
  /**
   * Sets the email address of the query unless it is null. 
   * If null, then leaves unchanged.
   * 
   * */

  public void setEmailAddress(String emailAddress) {
    if(emailAddress == null){
      return;
    }
    
    this.emailAddress = emailAddress;
  }

  public String getNote() {
    return note;
  }

  /**
   * Sets the note of the query unless it is null. If null, then leaves unchanged.
   * 
   * */
  
  public void setNote(String note) {
    if(note == null){
      return;
    }
    this.note = note;
  }
  
  /**
   * Sets the name of the entry. If name is null or only has white spaces, 
   * then it is not changed
   * 
   * */

  public void setName(String name) {
    if(name == null){
      return;
    }
    
    if(name.replace(" \n\t", "").length() == 0){
      return;
    }
    this.name = name;
  }
  
}