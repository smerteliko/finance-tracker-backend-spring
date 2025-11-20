package com.financetracker.controller;

import com.financetracker.dto.category.CategoryRequest;
import com.financetracker.dto.category.CategoryResponse;
import com.financetracker.enums.CategoryType;
import com.financetracker.services.Category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Management of income and expense categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Retrieve all user categories, optionally filtered by type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
        @Parameter(description = "Filter by category type (INCOME or EXPENSE)") @RequestParam(value = "type", required = false) CategoryType type) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

//    @Operation(summary = "Retrieve a category by ID")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
//        @ApiResponse(responseCode = "401", description = "Unauthorized"),
//        @ApiResponse(responseCode = "404", description = "Category not found")
//    })
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable UUID id) {
//        CategoryResponse category = categoryService.(id);
//        return ResponseEntity.ok(category);
//    }

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Category successfully created", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request (validation error)"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.createCategory(request);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category successfully updated", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request (validation error)"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}