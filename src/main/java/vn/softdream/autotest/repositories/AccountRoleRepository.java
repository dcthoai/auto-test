package vn.softdream.autotest.repositories;

import vn.softdream.autotest.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {}
