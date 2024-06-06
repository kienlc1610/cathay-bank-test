package cathay.bank.interview.demo.service.mapper;

import cathay.bank.interview.demo.entity.DbMaintenanceManagement;
import cathay.bank.interview.demo.service.dto.DbMaintenanceManagementDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link DbMaintenanceManagement} and its DTO {@link DbMaintenanceManagementDTO}.
 */
@Mapper(componentModel = "spring")
public interface DbMaintenanceManagementMapper extends EntityMapper<DbMaintenanceManagementDTO, DbMaintenanceManagement> {}
