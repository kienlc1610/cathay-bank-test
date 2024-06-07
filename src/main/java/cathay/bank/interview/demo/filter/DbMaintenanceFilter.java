package cathay.bank.interview.demo.filter;

import cathay.bank.interview.demo.service.DbMaintenanceManagementService;
import cathay.bank.interview.demo.service.dto.DbMaintenanceManagementDTO;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DbMaintenanceFilter implements HandlerInterceptor, Filter {
    private final DbMaintenanceManagementService dbMaintenanceManagementService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map<String, List<DbMaintenanceManagementDTO>> status = dbMaintenanceManagementService.getMaintenanceStatus();
        String url = ((HttpServletRequest)servletRequest).getRequestURL().toString();

        if (url.contains("/api/currencies") && Optional.ofNullable(status.get("currency")).isPresent()) {
            boolean isActivated = status.get("currency").stream().allMatch(DbMaintenanceManagementDTO::getActivated);

            if (!isActivated) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Currencies are not activated");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
