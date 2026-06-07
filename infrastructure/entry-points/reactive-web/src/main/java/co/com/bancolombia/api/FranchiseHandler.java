package co.com.bancolombia.api;

import co.com.bancolombia.usecase.franchise.CreateFranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {

    private final CreateFranchiseUseCase createFranchiseUseCase;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {

        return request.bodyToMono(CreateFranchiseRequest.class)
                .flatMap(dto ->
                        createFranchiseUseCase.execute(dto.toFranchise())
                )
                .flatMap(franchise ->
                        ServerResponse.ok().bodyValue(franchise)
                );
    }
}
