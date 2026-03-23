package ap1.maylin.jauregui.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("product")
@Schema(description = "Product entity")
public class Product {
    
    @Id
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;
    
    @Schema(description = "Name of the product", example = "Laptop Dell XPS 15")
    private String name;
    
    @Schema(description = "Detailed description of the product", example = "High-performance laptop with 16GB RAM and 512GB SSD")
    private String description;
    
    @Schema(description = "Price of the product", example = "1299.99")
    private BigDecimal price;
    
    @Schema(description = "Category of the product", example = "Electronics")
    private String category;
    
    @Schema(description = "Available stock quantity", example = "50")
    private Integer stock;
    
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Whether the product is active", example = "true")
    private Boolean active;
}
