package com.example.mediamarkt.product.interfaces.controllers;

import com.example.mediamarkt.product.application.impl.ManageCategoryImpl;
import com.example.mediamarkt.product.interfaces.controllers.dto.CategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Categoy", description = "Endpoint to handle Categories")
public class CategoryController {

  private final ManageCategoryImpl categoryImpl;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create new Category")
  public Mono<CategoryDto> create(@RequestBody Mono<CategoryDto> categoryDto) {

    return categoryDto
        .map(CategoryDto::toDomain)
        .flatMap(c -> categoryImpl.createCategory(c))
        .map(CategoryDto::fromDomain);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find category by Id")
  public Mono<CategoryDto> getById(@PathVariable String id) {
    return categoryImpl.getCategory(id).map(CategoryDto::fromDomain);
  }

  @GetMapping("/all")
  @Operation(summary = "Find all categories")
  public Flux<CategoryDto> getAll() {
    return categoryImpl.getAllCategories().map(CategoryDto::fromDomain);
  }

  @GetMapping("/{id}/path")
  @Operation(summary = "Get the entire path of category tree")
  public Mono<List<CategoryDto>> getEntirePathCategory(@PathVariable String id) {
    return categoryImpl
        .getCategoryPath(id)
        .map(list -> list.stream().map(CategoryDto::fromDomain).toList());
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Update category")
  public Mono<CategoryDto> update(
      @PathVariable String id, @RequestBody Mono<CategoryDto> categoryDto) {

    return categoryDto
        .map(CategoryDto::toDomain)
        .flatMap(c -> categoryImpl.updateCategory(id, c))
        .map(CategoryDto::fromDomain);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Delete category")
  public Mono<Void> delete(@PathVariable String id) {
    return categoryImpl.deleteCategoryById(id);
  }

  @DeleteMapping()
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Delete all categories")
  public Mono<Void> deleteAll() {
    return categoryImpl.deleteCategories();
  }
}
