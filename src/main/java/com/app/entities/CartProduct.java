package com.app.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  private int quantity;

}
