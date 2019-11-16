package kot.hou.fullapprxjava.service;

import kot.hou.fullapprxjava.model.CoinBaseResponse;
import reactor.core.publisher.Mono;

public interface CoinBaseService {
    Mono<CoinBaseResponse> getCrypotoPrice(String priceName);

}
