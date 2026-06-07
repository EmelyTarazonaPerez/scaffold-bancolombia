package co.com.bancolombia.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("franchises")
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
