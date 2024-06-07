package cathay.bank.interview.demo.service;

import cathay.bank.interview.demo.entity.Currency;
import cathay.bank.interview.demo.service.dto.CoinDeskPriceResponseDto;
import cathay.bank.interview.demo.service.dto.CurrencyDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CurrencyService {
    CurrencyDTO save(CurrencyDTO currencyDTO) throws BadRequestException;

    CurrencyDTO update(CurrencyDTO currencyDTO);

    Optional<CurrencyDTO> partialUpdate(CurrencyDTO currencyDTO);

    @Transactional(readOnly = true)
    Page<CurrencyDTO> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    Optional<CurrencyDTO> findOne(String code);

    void delete(String code);

    List<Currency> findAll();

    CompletableFuture<Void> sync();
}
