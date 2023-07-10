package com.app.entities;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class User extends BaseEntity {

  // validator
  @NotNull(message = "Name cannot be null")
  @NotBlank(message = "Name cannot be empty")
  @Size(min = 3, max = 60, message = "Name has to be 3 - 60 characters")
  // sql
  @Column(nullable = false, length = 60)
  private String name;

  // validator
  @NotNull(message = "Lastname cannot be null")
  @NotBlank(message = "Lastname cannot be empty")
  @Size(min = 3, max = 60, message = "Lastname has to be 3 - 60 characters")
  // sql
  @Column(nullable = false, length = 60)
  private String lastname;

  // validator
  @NotNull(message = "DNI cannot be null")
  // sql
  @Column(nullable = false, length = 8, unique = true)
  private int dni;

  // validator
  @Email(message = "Invalid email format")
  // sql
  @Column(nullable = false, length = 60, unique = true)
  private String email;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  private Address address;

}
