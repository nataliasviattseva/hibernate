package fr.ensitech.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "users")
@NamedQueries({
    @NamedQuery(
        name = "User::findAll",
        query = "FROM User u"),
    @NamedQuery(
        name = "User::ByNameAndLastName",
        query = "FROM User u WHERE u.lastName = ?1 AND u.name = ?2"),
    @NamedQuery(
        name = "User::ByCity",
        query = "SELECT DISTINCT u FROM User u JOIN u.addresses a WHERE a.city = :city")
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "last_name", nullable = false, length = 45)
  @NotEmpty(message = "Last name cannot be empty")
  @Length(min = 1, max = 45, message = "Last name must be between 1 and 45 characters")
  private String lastName;

  @Column(name = "name", nullable = false, length = 45)
  @NotEmpty(message = "Name cannot be empty")
  @Length(min = 1, max = 45, message = "Name must be between 1 and 45 characters")
  private String name;

  @Column(name = "email", nullable = false, unique = true, length = 100)
  @NotEmpty(message = "Email cannot be empty")
  @Email(message = "Email should be valid")
  @Length(min = 6, max = 100, message = "Email must be less than 100 characters")
  private String email;

  @Column(name = "password", nullable = false, length = 100)
  @NotEmpty(message = "Password cannot be empty")
  @Length(min = 6, max = 24, message = "Password must be between 6 and 24 characters")
  private String password;

  @Column(name = "birth_date", nullable = true)
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Address> addresses;

  public User() {
    // Default constructor
    this.addresses = new ArrayList<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", lastName='" + lastName + '\'' +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", birthDate=" + birthDate +
        ", isActive=" + isActive +
        '}';
  }
}
