package com.app.services.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.entities.Brand;
import com.app.entities.Category;
import com.app.entities.Product;
import com.app.repositories.BaseRepository;
import com.app.repositories.BrandRepository;
import com.app.repositories.CategoryRepository;
import com.app.repositories.ProductRepository;
import com.app.services.base.BaseServiceImpl;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  BrandRepository brandRepository;

  @Autowired
  CategoryRepository categoryRepository;

  public ProductServiceImpl(BaseRepository<Product, Long> baseRepository) {
    super(baseRepository);
  }

  /* -- SPECIFIC METHODS */

  @Override
  public Page<Product> listByBrand(String brand, Pageable pageable) throws Exception {

    try {

      String brandname = brand.replace("_", " ");

      Optional<Brand> optionalBrand = brandRepository.findByName(brandname);

      if (optionalBrand.isPresent()) {

        Brand searchedBrand = optionalBrand.get();

        Page<Product> productList = productRepository.listByBrand(searchedBrand.getId(), pageable);
        return productList;

      } else {
        throw new Exception("BRAND_NOT_FOUND");
      }

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public Page<Product> listByCategory(String category, Pageable pageable) throws Exception {
    try {

      String categoryname = category.replace("_", " ");

      Optional<Category> optionalCategory = categoryRepository.findByName(categoryname);

      if (optionalCategory.isPresent()) {

        Category searchedCategory = optionalCategory.get();

        Page<Product> productList = productRepository.listByCategory(searchedCategory.getId(), pageable);
        return productList;

      } else {
        throw new Exception("CATEGORY_NOT_FOUND");
      }

    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
