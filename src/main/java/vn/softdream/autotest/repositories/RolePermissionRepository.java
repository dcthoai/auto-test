package vn.softdream.autotest.repositories;

import vn.softdream.autotest.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {}
