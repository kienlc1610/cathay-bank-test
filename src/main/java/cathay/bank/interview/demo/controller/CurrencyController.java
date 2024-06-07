package cathay.bank.interview.demo.controller;

import cathay.bank.interview.demo.repository.CurrencyRepository;
import cathay.bank.interview.demo.service.CurrencyService;
import cathay.bank.interview.demo.service.impl.CurrencyServiceImpl;
import cathay.bank.interview.demo.service.dto.CurrencyDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final Logger log = LoggerFactory.getLogger(CurrencyController.class);

    private final CurrencyService currencyService;

    private final CurrencyRepository currencyRepository;

    /**
     * {@code POST  /currencies} : Create a new currency.
     *
     * @param currencyDTO the currencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currencyDTO,
     * or with status {@code 400 (Bad Request)} if the currency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CurrencyDTO> createCurrency(@Valid @RequestBody CurrencyDTO currencyDTO) throws
            URISyntaxException, BadRequestException {
        log.debug("REST request to save Currency : {}", currencyDTO);
        if (ObjectUtils.isEmpty(currencyDTO.getCode())) {
            return ResponseEntity.badRequest().body(null);
        }
        currencyDTO = currencyService.save(currencyDTO);
        return ResponseEntity.created(new URI("/api/currencies/" + currencyDTO.getCode()))
            .body(currencyDTO);
    }

    /**
     * {@code PUT  /currencies/:id} : Updates an existing currency.
     *
     * @param code          the id of the currencyDTO to save.
     * @param currencyDTO the currencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyDTO,
     * or with status {@code 400 (Bad Request)} if the currencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCurrency(
        @PathVariable(value = "id", required = false) final String code,
        @Valid @RequestBody CurrencyDTO currencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Currency : {}, {}", code, currencyDTO);
        if (ObjectUtils.isEmpty(currencyDTO.getCode())) {
            return ResponseEntity.badRequest().body("Code is null");
        }
        if (!Objects.equals(code, currencyDTO.getCode())) {
            return ResponseEntity.badRequest().body("Invalid code");
        }

        if (!currencyRepository.existsById(code)) {
            return ResponseEntity.badRequest().body("Not found");
        }

        currencyDTO = currencyService.update(currencyDTO);
        return ResponseEntity.ok()
            .body(currencyDTO);
    }

    /**
     * {@code PATCH  /currencies/:id} : Partial updates given fields of an existing currency, field will ignore if it is null
     *
     * @param code          the id of the currencyDTO to save.
     * @param currencyDTO the currencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currencyDTO,
     * or with status {@code 400 (Bad Request)} if the currencyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the currencyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the currencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Object> partialUpdateCurrency(
        @PathVariable(value = "id", required = false) final String code,
        @NotNull @RequestBody CurrencyDTO currencyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Currency partially : {}, {}", code, currencyDTO);
        if (ObjectUtils.isEmpty(currencyDTO.getCode())) {
            return ResponseEntity.badRequest().body("Code is null");
        }
        if (!Objects.equals(code, currencyDTO.getCode())) {
            return ResponseEntity.badRequest().body("Invalid code");
        }

        if (!currencyRepository.existsById(code)) {
            return ResponseEntity.badRequest().body("Not found");
        }

        Optional<CurrencyDTO> result = currencyService.partialUpdate(currencyDTO);

        return result.map((Function<? super CurrencyDTO, ? extends ResponseEntity<Object>>) ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code GET  /currencies} : get all the currencies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currencies in body.
     */
    @GetMapping("")
    public ResponseEntity<Page<CurrencyDTO>> getAllCurrencies
    (@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Currencies");
        Page<CurrencyDTO> page = currencyService.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /currencies/:id} : get the "id" currency.
     *
     * @param code the id of the currencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDTO> getCurrency(@PathVariable("id") String code) {
        log.debug("REST request to get Currency : {}", code);
        Optional<CurrencyDTO> currencyDTO = currencyService.findOne(code);
        return currencyDTO.map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code DELETE  /currencies/:id} : delete the "id" currency.
     *
     * @param code the id of the currencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable("id") String code) {
        log.debug("REST request to delete Currency : {}", code);
        currencyService.delete(code);
        return ResponseEntity.noContent()
            .build();
    }
}
