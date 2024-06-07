package cathay.bank.interview.demo.service;

import cathay.bank.interview.demo.service.dto.DbMaintenanceManagementDTO;

import java.util.List;
import java.util.Map;

public interface DbMaintenanceManagementService {
    Map<String, List<DbMaintenanceManagementDTO>> getMaintenanceStatus();

    void maintain(String serviceName);

    void unMaintain(String serviceName);
}
