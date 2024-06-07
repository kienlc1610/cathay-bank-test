package cathay.bank.interview.demo.config;

import cathay.bank.interview.demo.filter.DbMaintenanceFilter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final DbMaintenanceFilter dbMaintenanceFilter;
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

    @Bean
    public FilterRegistrationBean<DbMaintenanceFilter> loggingFilter(){
        FilterRegistrationBean<DbMaintenanceFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(dbMaintenanceFilter);
        registrationBean.addUrlPatterns("/api/currencies/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
