package co.com.bancolombia.model.franchise;
import co.com.bancolombia.model.branch.Branch;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Franchise {
    private String id;
    private Long version;
    private String name;
    @Builder.Default
    private List<Branch> branches = new ArrayList<>();

    public Franchise(String id, String name, List<Branch> branches) {
        this.id = id;
        this.name = name;
        this.branches = branches;
    }

}
