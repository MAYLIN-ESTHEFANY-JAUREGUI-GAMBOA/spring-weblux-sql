package ap1.maylin.jauregui.rest;

import ap1.maylin.jauregui.model.Product;
import ap1.maylin.jauregui.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "API for managing products")
public class ProductRestController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return productService.createProduct(product)
                .map(createdProduct -> ResponseEntity.status(HttpStatus.CREATED).body(createdProduct));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Mono<ResponseEntity<Product>> getProductById(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products from the database")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Mono<ResponseEntity<Product>> updateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate a product", description = "Activates an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product activated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Mono<ResponseEntity<Product>> activateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        return productService.getProductById(id)
                .flatMap(product -> {
                    product.setActive(true);
                    product.setUpdatedAt(java.time.LocalDateTime.now());
                    return productService.updateProduct(id, product);
                })
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a product", description = "Deactivates an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Mono<ResponseEntity<Product>> deactivateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        return productService.getProductById(id)
                .flatMap(product -> {
                    product.setActive(false);
                    product.setUpdatedAt(java.time.LocalDateTime.now());
                    return productService.updateProduct(id, product);
                })
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Mono<ResponseEntity<Void>> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        return productService.deleteProduct(id)
                .map(v -> ResponseEntity.noContent().<Void>build())
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/name")
    @Operation(summary = "Search products by name", description = "Searches products by name (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Products found")
    public Flux<Product> searchProductsByName(
            @Parameter(description = "Product name to search") @RequestParam String name) {
        return productService.getProductsByName(name);
    }

    @GetMapping("/search/category")
    @Operation(summary = "Get products by category", description = "Retrieves products by category")
    @ApiResponse(responseCode = "200", description = "Products found")
    public Flux<Product> getProductsByCategory(
            @Parameter(description = "Product category") @RequestParam String category) {
        return productService.getProductsByCategory(category);
    }

@GetMapping("/search/price-range")
    @Operation(summary = "Get products by price range", description = "Retrieves products within a price range")
    @ApiResponse(responseCode = "200", description = "Products found")
    public Flux<Product> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam Double maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active products", description = "Retrieves all active products")
    @ApiResponse(responseCode = "200", description = "Active products retrieved")
    public Flux<Product> getActiveProducts() {
        return productService.getActiveProducts();
    }

    @GetMapping("/in-stock")
    @Operation(summary = "Get products in stock", description = "Retrieves all products with stock > 0")
    @ApiResponse(responseCode = "200", description = "Products in stock retrieved")
    public Flux<Product> getProductsInStock() {
        return productService.getProductsInStock();
    }
}
