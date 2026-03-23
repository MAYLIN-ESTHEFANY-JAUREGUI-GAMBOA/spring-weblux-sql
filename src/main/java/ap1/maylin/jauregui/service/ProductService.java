package ap1.maylin.jauregui.service;

import ap1.maylin.jauregui.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    
    Mono<Product> createProduct(Product product);
    
    Mono<Product> getProductById(Long id);
    
    Flux<Product> getAllProducts();
    
    Mono<Product> updateProduct(Long id, Product product);
    
    Mono<Void> deleteProduct(Long id);
    
    Flux<Product> getProductsByName(String name);
    
    Flux<Product> getProductsByCategory(String category);
    
    Flux<Product> getProductsByPriceRange(Double minPrice, Double maxPrice);
    
    Flux<Product> getActiveProducts();
    
    Flux<Product> getProductsInStock();
    
    Mono<Product> activateProduct(Long id);
    
    Mono<Product> deactivateProduct(Long id);
}
