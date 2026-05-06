package com.example.mediamarkt.product.interfaces.controllers;

import com.example.mediamarkt.product.application.impl.ManageCategoryImpl;
import com.example.mediamarkt.product.application.impl.ManageProductsImpl;
import com.example.mediamarkt.product.interfaces.controllers.dto.CategoryDto;
import com.example.mediamarkt.product.interfaces.controllers.dto.ProductDto;
import com.example.mediamarkt.product.interfaces.controllers.dto.ProductWithCategoryPathDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Products", description = "Endpoint to handle Products")
public class ProductController {

  private final ManageProductsImpl productsImpl;
  private final ManageCategoryImpl categoryImpl;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create new Product")
  public Mono<ProductDto> create(@Valid @RequestBody Mono<ProductDto> productDto) {
    return productDto
        .map(ProductDto::toDomain)
        .flatMap(productsImpl::createProduct)
        .map(ProductDto::fromDomain);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Product by ID")
  public Mono<ProductDto> getById(@PathVariable String id) {

    return productsImpl.getProduct(id).map(ProductDto::fromDomain);
  }

  @GetMapping("/all")
  @Operation(summary = "Get all Products")
  public Flux<ProductDto> getAllProducts() {
    return productsImpl.getAllProducts().map(ProductDto::fromDomain);
  }

  @GetMapping("/{id}/with-category-path")
  @Operation(summary = "Get the Product with the entire Category path")
  public Mono<ProductWithCategoryPathDto> getProductsWithCategoryPath(@PathVariable String id) {
    return productsImpl
        .getProduct(id) // find the product
        .flatMap(
            product -> {
              if (product.getCategoryIds() == null || product.getCategoryIds().isEmpty()) {
                log.info("Product with id {} has no categories", id);
                return Mono.just(
                    ProductWithCategoryPathDto.builder()
                        .product(ProductDto.fromDomain(product))
                        .categoryPaths(List.of())
                        .build());
              }

              Flux<List<CategoryDto>> pathsFlux =
                  Flux.fromIterable(product.getCategoryIds())
                      .flatMap(
                          catId ->
                              categoryImpl
                                  .getCategoryPath(catId)
                                  .doOnSuccess(
                                      list ->
                                          log.info(
                                              "Finished processing category path for catId {} with {} categories",
                                              catId,
                                              list.size()))
                                  .map(
                                      list ->
                                          list.stream()
                                              .map(CategoryDto::fromDomain)
                                              .collect(Collectors.toList()))
                                  .onErrorResume(e -> Mono.empty()));

              return pathsFlux
                  .collectList()
                  .map(
                      paths ->
                          ProductWithCategoryPathDto.builder()
                              .product(ProductDto.fromDomain(product))
                              .categoryPaths(paths)
                              .build());
            });
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update product by Id")
  public Mono<ProductDto> update(
      @PathVariable String id, @Valid @RequestBody Mono<ProductDto> productDto) {

    return productDto
        .map(ProductDto::toDomain)
        .flatMap(domain -> productsImpl.updateProduct(id, domain))
        .map(ProductDto::fromDomain);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Delete a product")
  public Mono<Void> delete(@PathVariable String id) {
    return productsImpl.deleteProduct(id);
  }
}
