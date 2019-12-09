package cn.cnwair.mes.security.rbac.repository;

import cn.cnwair.mes.security.rbac.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BasicRepository<User> {
    User findByUsername(String username);
}
