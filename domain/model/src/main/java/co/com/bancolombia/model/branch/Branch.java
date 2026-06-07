package co.com.bancolombia.model.branch;
import co.com.bancolombia.model.product.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Branch {
    private String id;
    private String name;
    private List<Product> products = new ArrayList<>();
}
