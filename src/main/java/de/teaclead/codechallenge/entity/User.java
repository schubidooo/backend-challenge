package de.teaclead.codechallenge.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "users")
public class User {
  @Id
  @Column
  private String mailAddress;
  @Column
  private String firstname;
  @Column
  private String surname;

  public User() {

  }

  public User(String mailAddress, String firstname, String surname) {
    this.mailAddress = mailAddress;
    this.firstname = firstname;
    this.surname = surname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getMailAddress() {
    return mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }


}
