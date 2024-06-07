package cathay.bank.interview.demo.controller;


import cathay.bank.interview.demo.service.DbMaintenanceManagementService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class DbMaintenanceController {
    private final Logger log = LoggerFactory.getLogger(CurrencyController.class);
    private final DbMaintenanceManagementService dbMaintenanceManagementService;

    /**
     * Maintain service
     */
    @PostMapping("/{serviceName}")
    public ResponseEntity<URI> maintainService(@PathVariable String serviceName) throws
            URISyntaxException {
        dbMaintenanceManagementService.maintain(serviceName);

        return ResponseEntity.accepted().body(new URI("/api/maintenance/" + serviceName));
    }

    /**
     * Un-maintain service
     */
    @PatchMapping("/{serviceName}")
    public ResponseEntity<URI> unMaintainService(@PathVariable String serviceName) throws
            URISyntaxException {
        dbMaintenanceManagementService.unMaintain(serviceName);

        return ResponseEntity.accepted().body(new URI("/api/maintenance/" + serviceName));
    }
}
