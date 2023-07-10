package com.app.services.order;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.entities.Cart;
import com.app.entities.CartProduct;
import com.app.entities.Order;
import com.app.entities.OrderDetail;
import com.app.entities.Product;
import com.app.entities.User;
import com.app.repositories.BaseRepository;
import com.app.repositories.CartProductRepository;
import com.app.repositories.CartRepository;
import com.app.repositories.OrderDetailRepository;
import com.app.repositories.OrderRepository;
import com.app.repositories.ProductRepository;
import com.app.repositories.UserRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

  @Autowired
  protected UserRepository userRepository;

  @Autowired
  protected OrderRepository orderRepository;

  @Autowired
  protected OrderDetailRepository orderDetailRepository;

  @Autowired
  protected ProductRepository productRepository;

  @Autowired
  protected CartRepository cartRepository;

  @Autowired
  protected CartProductRepository cartProductRepository;

  public OrderServiceImpl(BaseRepository<Order, Long> baseRepository) {
    super(baseRepository);

  }

  /* === CREATE ORDER === */

  @Override
  public Order create(Order order) throws Exception {

    try {

      // VALIDATE AND UPDATE PRODUCTS QUANTITY:
      List<OrderDetail> od = order.getDetails();
      double totalAmount = 0;

      for (OrderDetail o : od) {
        Optional<Product> pOptional = productRepository.findById(o.getProduct().getId());
        // check existence:
        if (!pOptional.isPresent()) {
          throw new Exception("PRODUCT_NOT_FOUND");
        }
        Product p = pOptional.get();
        // check stock:
        if (p.getStock() < o.getQuantity()) {
          throw new Exception("INVALID_QUANTITY");
        }
        // check amount:
        double subtotal = p.getPrice() * o.getQuantity();
        if (subtotal != o.getSubtotal()) {
          throw new Exception("INVALID_AMOUNT");
        }

        // update quantity and sum subtotal:
        p.setStock(p.getStock() - o.getQuantity());
        productRepository.save(p);
        o.setOrder(order); // bidirection
        totalAmount += o.getSubtotal();
      }
      // Check total amount:
      if (totalAmount != order.getTotalAmount()) {
        throw new Exception("INVALID_TOTAL_AMOUNT");
      }

      // clear cart:
      Optional<Cart> ucOptional = cartRepository.findByUser(order.getUser().getId());
      Long cid = ucOptional.get().getId();

      List<CartProduct> cartProducts = cartProductRepository.listProductCart(cid);
      if (!cartProducts.isEmpty()) {
        for (CartProduct cp : cartProducts) {
          cartProductRepository.deleteById(cp.getId());
        }
      }

      order.setDate(new Date()); // set timestamp
      // create and return order:
      return orderRepository.save(order);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  /* === LIST USER ORDERS === */

  @Override
  public Page<Order> listOrders(Long uid, Pageable pageable) throws Exception {
    try {
      Optional<User> ou = userRepository.findById(uid);

      if (!ou.isPresent())
        throw new Exception("USER_NOT_FOUND");

      return orderRepository.listOrders(uid, pageable);

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

}
