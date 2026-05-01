package com.example.mediamarkt.product.infraestructure.bootstrap;

import com.example.mediamarkt.product.application.port.CategoryRepositoryPort;
import com.example.mediamarkt.product.application.port.ProductRepositoryPort;
import com.example.mediamarkt.product.domain.model.Category;
import com.example.mediamarkt.product.domain.model.Product;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {
  private final CategoryRepositoryPort categoryRepo;
  private final ProductRepositoryPort productRepo;

  @EventListener(ApplicationReadyEvent.class)
  public void loadData() {
    categoryRepo
        .findAll()
        .hasElements()
        .flatMap(
            hasCategories -> {
              if (!hasCategories) {
                log.info("No categories found, initializing loading Data from Excel...");
                return loadCategories().then(loadProducts());
              } else {
                log.info("Data already exists. Skipping initialization.");
                return Mono.empty();
              }
            })
        .doOnSuccess(l -> log.info("Data loaded successfully"))
        .doOnError(e -> log.error("Failed to load data", e))
        .subscribe();
  }

  private Mono<Void> loadCategories() {
    List<Category> categories = new ArrayList<>();
    File file = new File("src/main/resources/data/categories_dataset.xlsx");
    if (!file.exists()) {
      log.warn("Categories Excel file not found at path: {}", file.getAbsolutePath());
      return Mono.empty();
    }

    try (FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis)) {
      Sheet sheet = workbook.getSheetAt(0);
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        if (row == null || row.getCell(0) == null) continue;

        String id = getCellValueAsString(row, 0);
        String name = getCellValueAsString(row, 1);
        String parentId = getCellValueAsString(row, 2);

        if (id != null && !id.trim().isEmpty()) {
          categories.add(Category.builder().id(id).name(name).parentId(parentId).build());
        }
      }
    } catch (Exception e) {
      log.error("Failed to parse categories excel", e);
    }

    return Flux.fromIterable(categories).flatMap(categoryRepo::save).then();
  }

  private Mono<Void> loadProducts() {
    List<Product> products = new ArrayList<>();
    File file = new File("src/main/resources/data/products_dataset.xlsx");
    if (!file.exists()) {
      log.warn("Products Excel file not found at path: {}", file.getAbsolutePath());
      return Mono.empty();
    }

    try (FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis)) {
      Sheet sheet = workbook.getSheetAt(0);
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        Row row = sheet.getRow(i);
        if (row == null || row.getCell(0) == null) continue;

        String name = getCellValueAsString(row, 0);
        String categoryRefs = getCellValueAsString(row, 1);
        String status = getCellValueAsString(row, 2);
        String longDesc = getCellValueAsString(row, 3);
        String shortDesc = getCellValueAsString(row, 4);

        List<String> categoryIds = new ArrayList<>();
        if (categoryRefs != null && !categoryRefs.trim().isEmpty()) {
          categoryIds = Arrays.asList(categoryRefs.split(";"));
        }

        if (name != null && !name.trim().isEmpty()) {
          products.add(
              Product.builder()
                  .name(name)
                  .status(status)
                  .shortDescription(shortDesc)
                  .longDescription(longDesc)
                  .categoryIds(categoryIds)
                  .build());
        }
      }
    } catch (Exception e) {
      log.error("Failed to parse products excel", e);
    }

    return Flux.fromIterable(products).flatMap(productRepo::save).then();
  }

  private String getCellValueAsString(Row row, int index) {
    if (row.getCell(index) == null) return null;
    switch (row.getCell(index).getCellType()) {
      case STRING:
        return row.getCell(index).getStringCellValue().trim();
      case NUMERIC:
        // Removing decimal points for numeric IDs
        double val = row.getCell(index).getNumericCellValue();
        if (val == (long) val) {
          return String.format("%d", (long) val);
        } else {
          return String.format("%s", val);
        }
      default:
        return row.getCell(index).toString().trim();
    }
  }
}
