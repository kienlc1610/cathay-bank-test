package cathay.bank.interview.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CoinDeskBPIResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6881806989056723943L;

    private String code;
    private String symbol;
    private String rate;
    private String description;
    @JsonProperty("rate_float")
    private Float rateFloat;
}
