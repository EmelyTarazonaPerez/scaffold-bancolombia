package co.com.bancolombia.mongo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface FranchiseMongoRepository extends
        ReactiveMongoRepository<FranchiseData, String>,
        ReactiveQueryByExampleExecutor<FranchiseData> {

    @Aggregation(pipeline = {
            "{ $match: { id: ?0 } }",
            "{ $unwind: '$branches' }",
            "{ $set: { " +
                    "'branches.products': { " +
                    "$filter: { " +
                    "input: '$branches.products', " +
                    "as: 'product', " +
                    "cond: { " +
                    "$eq: [ " +
                    "'$$product.stock', " +
                    "{ $max: '$branches.products.stock' }" +
                    "] " +
                    "} " +
                    "} " +
                    "} " +
                    "} }",
            "{ $group: { " +
                    "_id: '$id', " +
                    "id: { $first: '$id' }, " +
                    "name: { $first: '$name' }, " +
                    "branches: { $push: '$branches' } " +
                    "} }",
            "{ $project: { _id: '$id', name: 1, branches: 1 } }"
    })
    Mono<FranchiseData> findMaxStockProductByBranch(String franchiseId);
}
