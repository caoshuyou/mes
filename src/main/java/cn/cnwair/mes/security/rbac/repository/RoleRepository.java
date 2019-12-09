package cn.cnwair.mes.security.rbac.repository;

import cn.cnwair.mes.security.rbac.entity.Role;
import cn.cnwair.mes.security.rbac.entity.RoleUser;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BasicRepository<Role> {

}
