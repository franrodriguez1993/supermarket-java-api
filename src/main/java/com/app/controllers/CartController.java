package com.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Cart;
import com.app.entities.CartProduct;
import com.app.services.cart.CartServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/cart")
public class CartController extends BaseControllerImpl<Cart, CartServiceImpl> {

  /* ====== ADD PRODUCT CART ====== */

  @PostMapping("/product")
  public ResponseEntity<?> addProduct(@RequestBody CartProduct cartProduct) throws Exception {
    try {

      return ResponseEntity.status(HttpStatus.CREATED).body(service.addProduct(cartProduct));
    } catch (Exception e) {
      if (e.getMessage().equals("CART_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CART_NOT_FOUND");
      } else if (e.getMessage().equals("PRODUCT_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PRODUCT_NOT_FOUND");
      } else if (e.getMessage().equals("INVALID_QUANTITY")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID_QUANTITY");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }

    }
  }

  /* ====== UPDATE PRODUCT CART ====== */

  @PutMapping("/product")
  public ResponseEntity<?> updateProduct(@RequestBody CartProduct cartProduct) throws Exception {
    try {

      return ResponseEntity.status(HttpStatus.OK).body(service.updateProduct(cartProduct));

    } catch (Exception e) {
      if (e.getMessage().equals("CART_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CART_NOT_FOUND");
      } else if (e.getMessage().equals("PRODUCT_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PRODUCT_NOT_FOUND");
      } else if (e.getMessage().equals("INVALID_QUANTITY")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID_QUANTITY");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* ====== LIST PRODUCT CART ====== */

  @GetMapping("/product/{uid}")
  public ResponseEntity<?> listProducts(@PathVariable Long uid) throws Exception {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(service.listProducts(uid));

    } catch (Exception e) {
      if (e.getMessage().equals("CART_NOT_FOUND")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CART_NOT_FOUND");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
      }
    }
  }

  /* ====== DELETE PRODUCT CART ====== */
  @DeleteMapping("/product/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws Exception {

    try {

      boolean res = service.deleteProduct(id);

      if (res) {
        return ResponseEntity.status(HttpStatus.OK).body("PRODUCT_DELETED");
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PRODUCT_NOT_FOUND");
      }

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("SERVER_ERROR");
    }
  }

}
