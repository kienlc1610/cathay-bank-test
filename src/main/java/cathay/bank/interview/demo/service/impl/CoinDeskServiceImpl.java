package cathay.bank.interview.demo.service.impl;

import cathay.bank.interview.demo.service.CoinDeskService;
import cathay.bank.interview.demo.service.dto.CoinDeskPriceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoinDeskServiceImpl implements CoinDeskService {
    private final RestClient restClient;

    @Override
    public Optional<CoinDeskPriceResponseDto> inquiryCoinDeskPrice(String code) {
        String CURRENT_PRICE_URL = "/currentprice/";
        String JSON_SUFFIX = ".json";
        String BPI_URL = "https://api.coindesk.com/v1/bpi";
        String url = BPI_URL + CURRENT_PRICE_URL + code + JSON_SUFFIX;
        try {
            CoinDeskPriceResponseDto res = restClient.get().uri(url).retrieve().body(CoinDeskPriceResponseDto.class);

            return Optional.ofNullable(res);
        } catch (RestClientResponseException e) {
            return Optional.empty();

        }
    }
}
