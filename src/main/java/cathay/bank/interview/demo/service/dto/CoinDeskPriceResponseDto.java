package cathay.bank.interview.demo.service.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class CoinDeskPriceResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -8242768886958419834L;

    private CoinDeskTimeResponseDto time;
    private String disclaimer;
    private String chartName;
    private Map<String, CoinDeskBPIResponseDto> bpi = new HashMap<>();
}
