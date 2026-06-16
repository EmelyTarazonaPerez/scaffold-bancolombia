package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.utils.Constans;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Document(Constans.FRANCHISES_COLLECTION)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranchiseData {
    @Id
    private String id;
    private String name;
    private List<BranchData> branches;
}
