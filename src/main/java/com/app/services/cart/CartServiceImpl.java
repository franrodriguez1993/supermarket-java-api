package com.app.services.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.Cart;
import com.app.entities.CartProduct;
import com.app.entities.Product;
import com.app.repositories.BaseRepository;
import com.app.repositories.CartProductRepository;
import com.app.repositories.CartRepository;
import com.app.repositories.ProductRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class CartServiceImpl extends BaseServiceImpl<Cart, Long> implements CartService {

  @Autowired
  CartProductRepository cartProductRepository;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  ProductRepository productRepository;

  public CartServiceImpl(BaseRepository<Cart, Long> baseRepository) {
    super(baseRepository);
  }

  /* ==== ADD CART PRODUCT ==== */

  @Override
  public CartProduct addProduct(CartProduct cartProduct) throws Exception {
    try {
      // CHECK IDS:
      Long cid = cartProduct.getCart().getId();
      Long pid = cartProduct.getProduct().getId();

      Optional<Cart> cartOptional = cartRepository.findById(cid);

      if (!cartOptional.isPresent()) {
        throw new Exception("CART_NOT_FOUND");
      }

      Optional<Product> productOptional = productRepository.findById(pid);

      if (!productOptional.isPresent()) {
        throw new Exception("PRODUCT_NOT_FOUND");
      }

      Product product = productOptional.get();

      if (product.getStock() < cartProduct.getQuantity()) {
        throw new Exception("INVALID_QUANTITY");
      }

      // UPDATE IF EXISTS:

      Optional<CartProduct> CPOptional = cartProductRepository.findCartProduct(cid, pid);

      if (CPOptional.isPresent()) {
        CartProduct ucp = CPOptional.get();
        ucp.setQuantity(ucp.getQuantity() + cartProduct.getQuantity());
        return cartProductRepository.save(ucp);
      }

      // CREATE NEW IF NOT EXISTS:

      return cartProductRepository.save(cartProduct);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* ==== UPDATE CART PRODUCT ==== */

  @Override
  public CartProduct updateProduct(CartProduct cartProduct) throws Exception {

    try {
      // CHECK IDS:
      Long cid = cartProduct.getCart().getId();
      Long pid = cartProduct.getProduct().getId();

      Optional<Cart> cartOptional = cartRepository.findById(cid);

      if (!cartOptional.isPresent()) {
        throw new Exception("CART_NOT_FOUND");
      }

      Optional<Product> productOptional = productRepository.findById(pid);

      if (!productOptional.isPresent()) {
        throw new Exception("PRODUCT_NOT_FOUND");
      }

      Product product = productOptional.get();

      if (product.getStock() < cartProduct.getQuantity()) {
        throw new Exception("INVALID_QUANTITY");
      }

      return cartProductRepository.save(cartProduct);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* ==== LIST CART PRODUCT ==== */

  @Override
  public List<CartProduct> listProducts(Long uid) throws Exception {
    try {
      Optional<Cart> cartOptional = cartRepository.findByUser(uid);

      if (!cartOptional.isPresent()) {
        throw new Exception("CART_NOT_FOUND");
      }
      Long cid = cartOptional.get().getId();

      List<CartProduct> listProducts = cartProductRepository.listProductCart(cid);

      return listProducts;

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }

  /* ==== DELETE CART PRODUCT ==== */

  @Override
  public boolean deleteProduct(Long id) throws Exception {

    try {
      // Check if exists:
      Optional<CartProduct> cpOptional = cartProductRepository.findById(id);
      if (!cpOptional.isPresent()) {
        return false;
      }

      cartProductRepository.deleteById(id);
      return true;

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
