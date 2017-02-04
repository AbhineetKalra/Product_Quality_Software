package edu.nyu.cs.pqs.addressbook;

import java.lang.reflect.Field;

/**
 * Fields that can be searched by in AddressBookEntry.
 */
enum AddressBookField {
  NAME, POSTAL_ADDRESS, PHONE_NUMBER, EMAIL_ADDRESS, NOTE;
}

public final class AddressBookEntry {
  private String name;
  private String postalAddress;
  private String phoneNumber;
  private String emailAddress;
  private String note;

  /**
   * Default constructor of AddressBookEntry. Initialize all the fields to empty strings.
   */
  public AddressBookEntry() {
    this("", "", "", "", "");
  }

  /**
   * The constructor of AddressBookEntry. If any of the field is null, it will initialize it to an
   * empty string.
   */
  public AddressBookEntry( String name, String postalAddress, String phoneNumber,
      String emailAddress, String note ) {
    if (name == null) {
      this.name = "";
    } else {
      this.name = name;
    }
    if (postalAddress == null) {
      this.postalAddress = "";
    } else {
      this.postalAddress = postalAddress;
    }
    if (phoneNumber == null) {
      this.phoneNumber = "";
    } else {
      this.phoneNumber = phoneNumber;
    }
    if (emailAddress == null) {
      this.emailAddress = "";
    } else {
      this.emailAddress = emailAddress;
    }
    if (note == null) {
      this.note = "";
    } else {
      this.note = note;
    }
  }

  /**
   * Serialize the AddressBookEntry object to string. This method should never throw
   * IllegalArgumentException or IllegalAccessException.
   * 
   * @return the serialized string of current AddressBookEntry object.
   */
  protected String serialize(){
    StringBuffer sb = new StringBuffer();
    Field[] fields = AddressBookEntry.class.getDeclaredFields();
    for (Field field : fields) {
      // Check whether the type is String.class. This operation guarantees we won't throw any
      // IllegalArgumentException or IllegalAccessException
      if (field.getType().isAssignableFrom(String.class)) {
        String str = "";
        try {
          field.setAccessible(true);
          str = String.valueOf(field.get(this));
        } catch (IllegalArgumentException | IllegalAccessException e) {
          // we should never reach here.
        }
        sb.append(str.length()).append('/').append(str);
      }
    }
    return sb.toString();
  }

  /**
   * Deserialize an AddressBookEntry object from a string.
   * 
   * @param input
   *          the string to be deserialized.
   * @return an AddressBookEntry object.
   */
  protected static AddressBookEntry deserialize(String input){
    AddressBookEntry addressBookEntry = new AddressBookEntry();
    Field[] fields = AddressBookEntry.class.getDeclaredFields();
    for (int i = 0, j = 0; j < fields.length && i < input.length(); j++) {
      int slash = input.indexOf('/', i);
      int size = Integer.valueOf(input.substring(i, slash));
      Field field = fields[j];
      // Check whether the type is String.class. This operation guarantees we won't throw any
      // IllegalArgumentException or IllegalAccessException
      if (field.getType().isAssignableFrom(String.class)) {
        String serializedValue = input.substring(slash + 1, slash + size + 1);
        try {
          field.setAccessible(true);
          field.set(addressBookEntry, (Object) serializedValue);
        } catch (IllegalArgumentException | IllegalAccessException e) {
          // we should never reach here.
        }
      }
      i = slash + size + 1;
    }
    return addressBookEntry;
  }

  /**
   * Get the name of the AddressBookEntry object.
   */
  public String getName(){
    return name;
  }

  /**
   * Set the name of the AddressBookEntry object.
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * Get the postalAddress of the AddressBookEntry object.
   */
  public String getPostalAddress(){
    return postalAddress;
  }

  /**
   * Set the postalAddress of the AddressBookEntry object.
   */
  public void setPostalAddress(String postalAddress){
    this.postalAddress = postalAddress;
  }

  /**
   * Get the phoneNumber of the AddressBookEntry object.
   */
  public String getPhoneNumber(){
    return phoneNumber;
  }

  /**
   * Set the phoneNumber of the AddressBookEntry object.
   */
  public void setPhoneNumber(String phoneNumber){
    this.phoneNumber = phoneNumber;
  }

  /**
   * Get the emailAddress of the AddressBookEntry object.
   */
  public String getEmailAddress(){
    return emailAddress;
  }

  /**
   * Set the emailAddress of the AddressBookEntry object.
   */
  public void setEmailAddress(String emailAddress){
    this.emailAddress = emailAddress;
  }

  /**
   * Get the note of the AddressBookEntry object.
   */
  public String getNote(){
    return note;
  }

  /**
   * Set the note of the AddressBookEntry object.
   */
  public void setNote(String note){
    this.note = note;
  }
}
