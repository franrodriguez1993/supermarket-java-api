package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.CartProduct;

public interface CartProductRepository extends BaseRepository<CartProduct, Long> {

  /* FIND PRODUCT CART */
  @Query(value = "SELECT * FROM cart_product WHERE cart_id = :cid AND product_id = :pid", nativeQuery = true)
  Optional<CartProduct> findCartProduct(@Param("cid") Long cid, @Param("pid") Long pid);

  /* FIND BY CART */
  @Query(value = "SELECT * FROM cart_product WHERE cart_id = :cid", nativeQuery = true)
  List<CartProduct> listProductCart(@Param("cid") Long cid);

}
