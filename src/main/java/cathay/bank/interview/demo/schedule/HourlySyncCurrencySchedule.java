package cathay.bank.interview.demo.schedule;

import cathay.bank.interview.demo.controller.CurrencyController;
import cathay.bank.interview.demo.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class HourlySyncCurrencySchedule {
    private final Logger log = LoggerFactory.getLogger(CurrencyController.class);

    private final CurrencyService currencyService;

    @Async
    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.MINUTES)
    public void scheduleTaskUsingCronExpression() {
        log.info("Sync currency start - {}", LocalDateTime.now());

        currencyService.sync().whenComplete((coinDesk, throwable) -> {
            if (throwable != null) {
                log.error(throwable.getMessage(), throwable);
                return;
            }

            log.info("Sync currency completed - {}", LocalDateTime.now());
        });

    }
}
