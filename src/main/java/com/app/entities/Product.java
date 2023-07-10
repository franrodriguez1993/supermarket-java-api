package com.app.entities;

import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Product extends BaseEntity {

  // validator
  @NotNull(message = "Name cannot be null")
  @NotBlank(message = "Name cannot be empty")
  @Size(min = 3, max = 60, message = "Name has to be 5 - 150 characters")
  // sql
  @Column(nullable = false, length = 150)
  private String name;

  // validator
  @NotNull(message = "Price cannot be null")
  @Min(value = 1, message = "Minimum price should be 1")
  @Max(value = 999999, message = "Max price should be 999999")
  // sql
  @Column
  private double price;

  // validator
  @NotNull(message = "Stock cannot be null")
  @Min(value = 1, message = "Minimum stock should be 1")
  @Max(value = 99999, message = "Max stock should be 99999")
  // sql
  @Column
  private int stock;

  @ManyToOne
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
