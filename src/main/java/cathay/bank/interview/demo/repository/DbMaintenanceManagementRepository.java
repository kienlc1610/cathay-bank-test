package cathay.bank.interview.demo.repository;

import cathay.bank.interview.demo.entity.DbMaintenanceManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DbMaintenanceManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DbMaintenanceManagementRepository extends JpaRepository<DbMaintenanceManagement, String> {}
