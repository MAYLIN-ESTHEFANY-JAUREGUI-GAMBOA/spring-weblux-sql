package ap1.maylin.jauregui.repository;

import ap1.maylin.jauregui.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    
    Flux<Product> findByNameContainingIgnoreCase(String name);
    
    Flux<Product> findByCategory(String category);
    
    Flux<Product> findByActive(Boolean active);
    
    Flux<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    Flux<Product> findByStockGreaterThan(Integer stock);
}
