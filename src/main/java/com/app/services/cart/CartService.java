package com.app.services.cart;

import java.util.List;

import com.app.entities.Cart;
import com.app.entities.CartProduct;
import com.app.services.base.BaseService;

public interface CartService extends BaseService<Cart, Long> {

  CartProduct addProduct(CartProduct cartProduct) throws Exception; // add product to cart

  CartProduct updateProduct(CartProduct cartProduct) throws Exception; // update quantity

  List<CartProduct> listProducts(Long uid) throws Exception; // return list

  boolean deleteProduct(Long id) throws Exception; // drop product from cart
}
