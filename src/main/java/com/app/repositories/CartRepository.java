package com.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Cart;

public interface CartRepository extends BaseRepository<Cart, Long> {

  /* FIND BY USER */
  @Query(value = "SELECT * FROM cart WHERE user_id = :uid", nativeQuery = true)
  Optional<Cart> findByUser(@Param("uid") Long uid);

}
