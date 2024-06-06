package cathay.bank.interview.demo.service;

import cathay.bank.interview.demo.entity.Currency;
import cathay.bank.interview.demo.repository.CurrencyRepository;
import cathay.bank.interview.demo.service.dto.CurrencyDTO;
import cathay.bank.interview.demo.service.mapper.CurrencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Currency}.
 */
@Service
@Transactional
public class CurrencyService {

    private final Logger log = LoggerFactory.getLogger(CurrencyService.class);

    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;

    public CurrencyService(CurrencyRepository currencyRepository, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    /**
     * Save a currency.
     *
     * @param currencyDTO the entity to save.
     * @return the persisted entity.
     */
    public CurrencyDTO save(CurrencyDTO currencyDTO) {
        log.debug("Request to save Currency : {}", currencyDTO);
        Currency currency = currencyMapper.toEntity(currencyDTO);
        currency = currencyRepository.save(currency);
        return currencyMapper.toDto(currency);
    }

    /**
     * Update a currency.
     *
     * @param currencyDTO the entity to save.
     * @return the persisted entity.
     */
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
    public void delete(String code) {
        log.debug("Request to delete Currency : {}", code);
        currencyRepository.deleteById(code);
    }
}
