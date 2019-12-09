package cn.cnwair.mes.security.rbac.repository;

import cn.cnwair.mes.security.rbac.entity.Resource;
import cn.cnwair.mes.security.rbac.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends BasicRepository<Resource> {
    Resource findByName(String name);
}
