package com.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "user_id", unique = true)
  private User user;
}
