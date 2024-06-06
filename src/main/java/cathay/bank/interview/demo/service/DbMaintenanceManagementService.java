package cathay.bank.interview.demo.service;

import cathay.bank.interview.demo.entity.DbMaintenanceManagement;
import cathay.bank.interview.demo.repository.DbMaintenanceManagementRepository;
import cathay.bank.interview.demo.service.dto.DbMaintenanceManagementDTO;
import cathay.bank.interview.demo.service.mapper.DbMaintenanceManagementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DbMaintenanceManagement}.
 */
@Service
@Transactional
public class DbMaintenanceManagementService {

    private final Logger log = LoggerFactory.getLogger(DbMaintenanceManagementService.class);

    private final DbMaintenanceManagementRepository dbMaintenanceManagementRepository;

    private final DbMaintenanceManagementMapper dbMaintenanceManagementMapper;

    public DbMaintenanceManagementService(
        DbMaintenanceManagementRepository dbMaintenanceManagementRepository,
        DbMaintenanceManagementMapper dbMaintenanceManagementMapper
    ) {
        this.dbMaintenanceManagementRepository = dbMaintenanceManagementRepository;
        this.dbMaintenanceManagementMapper = dbMaintenanceManagementMapper;
    }

    /**
     * Save a dbMaintenanceManagement.
     *
     * @param dbMaintenanceManagementDTO the entity to save.
     * @return the persisted entity.
     */
    public DbMaintenanceManagementDTO save(DbMaintenanceManagementDTO dbMaintenanceManagementDTO) {
        log.debug("Request to save DbMaintenanceManagement : {}", dbMaintenanceManagementDTO);
        DbMaintenanceManagement dbMaintenanceManagement = dbMaintenanceManagementMapper.toEntity(dbMaintenanceManagementDTO);
        dbMaintenanceManagement = dbMaintenanceManagementRepository.save(dbMaintenanceManagement);
        return dbMaintenanceManagementMapper.toDto(dbMaintenanceManagement);
    }

    /**
     * Update a dbMaintenanceManagement.
     *
     * @param dbMaintenanceManagementDTO the entity to save.
     * @return the persisted entity.
     */
    public DbMaintenanceManagementDTO update(DbMaintenanceManagementDTO dbMaintenanceManagementDTO) {
        log.debug("Request to update DbMaintenanceManagement : {}", dbMaintenanceManagementDTO);
        DbMaintenanceManagement dbMaintenanceManagement = dbMaintenanceManagementMapper.toEntity(dbMaintenanceManagementDTO);
        dbMaintenanceManagement = dbMaintenanceManagementRepository.save(dbMaintenanceManagement);
        return dbMaintenanceManagementMapper.toDto(dbMaintenanceManagement);
    }

    /**
     * Partially update a dbMaintenanceManagement.
     *
     * @param dbMaintenanceManagementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DbMaintenanceManagementDTO> partialUpdate(DbMaintenanceManagementDTO dbMaintenanceManagementDTO) {
        log.debug("Request to partially update DbMaintenanceManagement : {}", dbMaintenanceManagementDTO);

        return dbMaintenanceManagementRepository
            .findById(dbMaintenanceManagementDTO.getServiceName())
            .map(existingDbMaintenanceManagement -> {
                dbMaintenanceManagementMapper.partialUpdate(existingDbMaintenanceManagement, dbMaintenanceManagementDTO);

                return existingDbMaintenanceManagement;
            })
            .map(dbMaintenanceManagementRepository::save)
            .map(dbMaintenanceManagementMapper::toDto);
    }

    /**
     * Get all the dbMaintenanceManagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DbMaintenanceManagementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DbMaintenanceManagements");
        return dbMaintenanceManagementRepository.findAll(pageable).map(dbMaintenanceManagementMapper::toDto);
    }

    /**
     * Get one dbMaintenanceManagement by id.
     *
     * @param serviceName Service Name.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DbMaintenanceManagementDTO> findOne(String serviceName) {
        log.debug("Request to get DbMaintenanceManagement : {}", serviceName);
        return dbMaintenanceManagementRepository.findById(serviceName).map(dbMaintenanceManagementMapper::toDto);
    }

    /**
     * Delete the dbMaintenanceManagement by id.
     *
     * @param serviceName Service Name.
     */
    public void delete(String serviceName) {
        log.debug("Request to delete DbMaintenanceManagement : {}", serviceName);
        dbMaintenanceManagementRepository.deleteById(serviceName);
    }
}
