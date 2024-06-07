package cathay.bank.interview.demo.service;

import cathay.bank.interview.demo.service.dto.CoinDeskPriceResponseDto;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CoinDeskService {
    Optional<CoinDeskPriceResponseDto> inquiryCoinDeskPrice(String code);
}
