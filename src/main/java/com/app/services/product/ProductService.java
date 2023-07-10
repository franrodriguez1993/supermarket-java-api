package com.app.services.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.entities.Product;
import com.app.services.base.BaseService;

public interface ProductService extends BaseService<Product, Long> {

  Page<Product> listByBrand(String brand, Pageable pageable) throws Exception;

  Page<Product> listByCategory(String category, Pageable pageable) throws Exception;
}
