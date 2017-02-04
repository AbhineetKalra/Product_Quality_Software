package cs.nyu.edu.pqs;

/**
 * The <tt>Entry</tt> class is representation of address book contact composed of a Name, a Phone
 * Number, an optional postal address, an optional Email and an optional Note.
 */
public class Entry implements Comparable <Entry> {
  private final String name;
  private final String phoneNumber;
  private final String postalAddress;
  private final String emailAddress;
  private final String note;

  // Builder pattern to avoid multiple constructors.
  public static class Builder {
    // Required parameters
    private final String name;
    private final String phoneNumber;
    // Optional parameters - initialized to default values.
    private String postalAddress = "";
    private String emailAddress = "";
    private String note = "";

    /**
     * name and phoneNumber are required to make an <tt>Entry</tt>.
     * 
     * @param name
     *          value to be set. It can't be null or blank. Escape and special characters are
     *          allowed.
     * @param phoneNumber
     *          value to be set. It can't be null or blank. Escape and special characters are
     *          allowed.
     * @throws IllegalArgumentException
     *           When name or phoneNumber is set to null or blank.
     */
    public Builder( String name, String phoneNumber ) {
      if (name == null || name.equals("") || phoneNumber == null || phoneNumber.equals("")) {
        throw new IllegalArgumentException("Name or phoneNumber cannot be null or blank");
      } else {
        this.name = name;
        this.phoneNumber = phoneNumber;
      }
    }

    /**
     * It is an optional attribute. The default value is "".
     * 
     * @param address
     *          value to be set. It can be null. Escape and special characters are allowed.
     * @return updated <tt>Entry</tt>
     */
    public Builder postalAddress(String address){
      this.postalAddress = address;
      return this;
    }

    /**
     * It is an optional attribute. The default value is "".
     * 
     * @param email
     *          value to be set. It can be null. Escape and special characters are allowed.
     * @return updated <tt>Entry</tt>
     */
    public Builder emailAddress(String email){
      this.emailAddress = email;
      return this;
    }

    /**
     * It is an optional attribute. The default value is "".
     * 
     * @param notes
     *          value to be set. It can be null. Escape and special characters are allowed.
     * @return updated <tt>Entry</tt>
     */
    public Builder note(String notes){
      this.note = notes;
      return this;
    }

    /**
     * Builds the Entry from builder pattern
     * 
     * @return Entry object
     */
    public Entry build(){
      return new Entry(this);
    }
  }

  private Entry( Builder builder ) {
    name = builder.name;
    phoneNumber = builder.phoneNumber;
    postalAddress = builder.postalAddress;
    emailAddress = builder.emailAddress;
    note = builder.note;
  }

  /**
   * Provides <tt>Entry's</tt> name.
   * 
   * @return It can be null.
   */
  public String getName(){
    return this.name;
  }

  /**
   * Provides <tt>Entry's</tt> phoneNumber.
   * 
   * @return It can be null.
   */
  public String getPhoneNumber(){
    return this.phoneNumber;
  }

  /**
   * Provides <tt>Entry's</tt> postalAddress.
   * 
   * @return It can be null.
   */
  public String getPostalAddress(){
    return this.postalAddress;
  }

  /**
   * Provides <tt>Entry's</tt> emailAddress.
   * 
   * @return It can be null.
   */
  public String getEmailAddress(){
    return this.emailAddress;
  }

  /**
   * Provides <tt>Entry's</tt> Note.
   * 
   * @return It can be null.
   */
  public String getNote(){
    return this.note;
  }

  // Overridden toString() method to provide readability of details in Entry.
  @Override
  public String toString(){
    String returnString = "Name: " + this.name + "; Number: " + this.phoneNumber;
    if (this.postalAddress != "") {
      returnString = returnString + "; Postal Address: " + this.postalAddress;
    }
    if (this.emailAddress != "") {
      returnString = returnString + "; Email: " + this.emailAddress;
    }
    if (this.note != "") {
      returnString = returnString + "; Notes: " + this.note;
    }
    return returnString;
  }

  // Method to compare two Strings when they are not null.
  private boolean stringEquality(String input1, String input2){
    if (input1 != null && input2 != null) {
      return (input1.equals(input2));
    } else
      return false;
  }

  // Overridden equals() method to check for logical equality of Entries.
  @Override
  public boolean equals(Object o){
    Entry other = (Entry) o;
    if ( (o instanceof Entry) && ! (o == null)) {
      if (o == this) {
        return true;
      } else if (!stringEquality(this.name, other.name)) {
        return false;
      } else if (!stringEquality(this.phoneNumber, other.phoneNumber)) {
        return false;
      } else if (!stringEquality(this.emailAddress, other.emailAddress)) {
        return false;
      } else if (!stringEquality(this.postalAddress, other.postalAddress)) {
        return false;
      } else if (!stringEquality(this.note, other.note)) {
        return false;
      }
    }
    return true;
  }

  // Overridden hashCode() method to produce equal hashCode for equal Entries.
  @Override
  public int hashCode(){
    final int prime = 31;
    int result = 1;
    result = prime * result + ( (emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ( (name == null) ? 0 : name.hashCode());
    result = prime * result + ( (note == null) ? 0 : note.hashCode());
    result = prime * result + ( (phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result + ( (postalAddress == null) ? 0 : postalAddress.hashCode());
    return result;
  }

  // Overridden compareTo() method to sort the Entries in phoneBook by name.
  @Override
  public int compareTo(Entry arg0){
    return this.getName().compareToIgnoreCase(arg0.getName());
  }
}