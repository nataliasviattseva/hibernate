package fr.ensitech.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "address")
@NamedQueries({
    @NamedQuery(
        name = "Address.findByCity",
        query = "SELECT a FROM Address a WHERE a.city = ?1"),
})
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "number", nullable = false, length = 10)
  private String number;

  @Column(name = "street", nullable = true, length = 64)
  private String street;

  @Column(name = "city", nullable = false, length = 45)
  @NotEmpty
  @Length(min = 1, max = 45, message = "City must be between 1 and 45 characters")
  private String city;

  @Column(name = "postal_code", nullable = false, length = 5)
  @NotEmpty
  @Length(min = 5, max = 5, message = "Postal code must be exactly 5 characters")
  private String postalCode;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Address() {
    // Default constructor
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Address{" +
        "id=" + id +
        ", number='" + number + '\'' +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", postalCode='" + postalCode + '\'' +
        '}';
  }
}
