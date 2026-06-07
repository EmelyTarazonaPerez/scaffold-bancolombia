package co.com.bancolombia.mongo;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseRepositoryAdapter
        extends AdapterOperations<
        Franchise,
        FranchiseData,
        String,
        FranchiseMongoRepository>
        implements FranchiseRepository {

    public FranchiseRepositoryAdapter(
            FranchiseMongoRepository repository,
            ObjectMapper mapper) {

        super(
                repository,
                mapper,
                d -> mapper.map(d, Franchise.class)
        );
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return repository.existsById(id);
    }
}