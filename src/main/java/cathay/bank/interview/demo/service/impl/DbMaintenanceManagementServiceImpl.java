package cathay.bank.interview.demo.service.impl;

import cathay.bank.interview.demo.entity.DbMaintenanceManagement;
import cathay.bank.interview.demo.repository.DbMaintenanceManagementRepository;
import cathay.bank.interview.demo.service.DbMaintenanceManagementService;
import cathay.bank.interview.demo.service.dto.DbMaintenanceManagementDTO;
import cathay.bank.interview.demo.service.mapper.DbMaintenanceManagementMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DbMaintenanceManagement}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DbMaintenanceManagementServiceImpl implements DbMaintenanceManagementService {

    private final Logger log = LoggerFactory.getLogger(DbMaintenanceManagementServiceImpl.class);

    private final DbMaintenanceManagementRepository dbMaintenanceManagementRepository;

    private final DbMaintenanceManagementMapper dbMaintenanceManagementMapper;

    @Override
    public Map<String, List<DbMaintenanceManagementDTO>> getMaintenanceStatus() {
        log.debug("Request to get all DbMaintenanceManagements");
        return dbMaintenanceManagementRepository.findAll().stream().map(dbMaintenanceManagementMapper::toDto)
                .collect(Collectors.groupingBy(DbMaintenanceManagementDTO::getServiceName));
    }

    @Override
    public void maintain(String serviceName) {
        Optional<DbMaintenanceManagement> data = dbMaintenanceManagementRepository.findById(serviceName);
        DbMaintenanceManagement entity = new DbMaintenanceManagement();
        if (data.isPresent()) {
            entity = data.get();
        } else {
            entity.setServiceName(serviceName);
        }

        entity.setActivated(true);

        dbMaintenanceManagementRepository.save(entity);
    }

    @Override
    public void unMaintain(String serviceName) {
        Optional<DbMaintenanceManagement> data = dbMaintenanceManagementRepository.findById(serviceName);
        DbMaintenanceManagement entity = new DbMaintenanceManagement();
        if (data.isPresent()) {
            entity = data.get();
        } else {
            entity.setServiceName(serviceName);
        }

        entity.setActivated(false);

        dbMaintenanceManagementRepository.save(entity);
    }
}
