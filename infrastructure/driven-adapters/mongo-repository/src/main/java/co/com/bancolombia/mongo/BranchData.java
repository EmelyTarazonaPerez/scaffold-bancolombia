package co.com.bancolombia.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchData {
    private String id;
    private String name;
    @Builder.Default
    private List<ProductData> products = new ArrayList<>();
}
