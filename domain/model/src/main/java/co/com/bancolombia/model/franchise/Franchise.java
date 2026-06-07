package co.com.bancolombia.model.franchise;
import co.com.bancolombia.model.branch.Branch;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches ;
}
