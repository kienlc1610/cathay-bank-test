package cathay.bank.interview.demo.service.mapper;

import cathay.bank.interview.demo.entity.Currency;
import org.mapstruct.Mapper;
import cathay.bank.interview.demo.service.dto.CurrencyDTO;

/**
 * Mapper for the entity {@link Currency} and its DTO {@link CurrencyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CurrencyMapper extends EntityMapper<CurrencyDTO, Currency> {}
