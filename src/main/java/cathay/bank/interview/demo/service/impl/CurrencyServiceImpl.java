package cathay.bank.interview.demo.service.impl;

import cathay.bank.interview.demo.entity.Currency;
import cathay.bank.interview.demo.repository.CurrencyRepository;
import cathay.bank.interview.demo.service.CoinDeskService;
import cathay.bank.interview.demo.service.CurrencyService;
import cathay.bank.interview.demo.service.dto.CoinDeskBPIResponseDto;
import cathay.bank.interview.demo.service.dto.CoinDeskPriceResponseDto;
import cathay.bank.interview.demo.service.dto.CurrencyDTO;
import cathay.bank.interview.demo.service.mapper.CurrencyMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Currency}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;

    private final CoinDeskService coinDeskService;

    /**
     * Save a currency.
     *
     * @param currencyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurrencyDTO save(CurrencyDTO currencyDTO) throws BadRequestException {
        log.debug("Request to save Currency : {}", currencyDTO);
        Currency currency = currencyMapper.toEntity(currencyDTO);

        if (currencyRepository.existsById(currency.getCode())) {
            throw new BadRequestException("Currency with code " + currencyDTO.getCode() + " already exists");
        }

        Optional<CoinDeskPriceResponseDto> inquiredCoinDeskResOption = coinDeskService.inquiryCoinDeskPrice(currency.getCode());
        if (inquiredCoinDeskResOption.isEmpty()) {
            throw new BadRequestException("Currency with code " + currencyDTO.getCode() + " is invalid");
        }

        CoinDeskPriceResponseDto inquiredCoinDeskRes = inquiredCoinDeskResOption.get();
        currency.setDescription(inquiredCoinDeskRes.getBpi().get(currency.getCode()).getDescription());
        currency.setSymbol(inquiredCoinDeskRes.getBpi().get(currency.getCode()).getSymbol());
        currency.setRate(inquiredCoinDeskRes.getBpi().get(currency.getCode()).getRate());
        currency.setRateFloat(inquiredCoinDeskRes.getBpi().get(currency.getCode()).getRateFloat());
        currency.setLatestSyncAt(LocalDateTime.now());
        currency = currencyRepository.save(currency);
        return currencyMapper.toDto(currency);
    }

    /**
     * Update a currency.
     *
     * @param currencyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurrencyDTO update(CurrencyDTO currencyDTO) {
        log.debug("Request to update Currency : {}", currencyDTO);
        Currency currency = currencyMapper.toEntity(currencyDTO);
        currency = currencyRepository.save(currency);
        return currencyMapper.toDto(currency);
    }

    /**
     * Partially update a currency.
     *
     * @param currencyDTO the entity to update partially.
     * @return the persisted entity.
     */
    @Override
    public Optional<CurrencyDTO> partialUpdate(CurrencyDTO currencyDTO) {
        log.debug("Request to partially update Currency : {}", currencyDTO);

        return currencyRepository
            .findById(currencyDTO.getCode())
            .map(existingCurrency -> {
                currencyMapper.partialUpdate(existingCurrency, currencyDTO);

                return existingCurrency;
            })
            .map(currencyRepository::save)
            .map(currencyMapper::toDto);
    }

    /**
     * Get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Currencies");
        if (ObjectUtils.isEmpty(pageable.getSort())) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("code"));
        }
        return currencyRepository.findAll(pageable).map(currencyMapper::toDto);
    }

    /**
     * Get one currency by id.
     *
     * @param code the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyDTO> findOne(String code) {
        log.debug("Request to get Currency : {}", code);
        return currencyRepository.findById(code).map(currencyMapper::toDto);
    }

    /**
     * Delete the currency by id.
     *
     * @param code the id of the entity.
     */
    @Override
    public void delete(String code) {
        log.debug("Request to delete Currency : {}", code);
        currencyRepository.deleteById(code);
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Async
    @Override
    public CompletableFuture<Void> sync() {
        List<CompletableFuture<Optional<CoinDeskPriceResponseDto>>> futures = this.findAll().stream().map(item -> CompletableFuture.supplyAsync(() -> coinDeskService.inquiryCoinDeskPrice(item.getCode()))).toList();
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).thenAccept(unused -> this.syncWithDB(futures.stream().map(CompletableFuture::join).toList()));
    }

    private void syncWithDB(List<Optional<CoinDeskPriceResponseDto>> coinDeskPrices) {
        List<Currency> entities = coinDeskPrices.stream().filter(Optional::isPresent).map(it -> {
            Currency currency = new Currency();
            CoinDeskPriceResponseDto price = it.get();


            Map.Entry<String, CoinDeskBPIResponseDto> entry = price.getBpi().entrySet().stream().findFirst().orElse(null);

            if (ObjectUtils.isEmpty(entry)) {
                return null;
            }

            var value = entry.getValue();
            currency.setCode(entry.getKey());
            currency.setDescription(value.getDescription());
            currency.setSymbol(value.getSymbol());
            currency.setRate(value.getRate());
            currency.setRateFloat(value.getRateFloat());
            currency.setLatestSyncAt(LocalDateTime.now());

            return currency;
        }).toList();

        currencyRepository.saveAll(entities);
    }
}
