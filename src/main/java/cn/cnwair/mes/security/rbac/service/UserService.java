package cn.cnwair.mes.security.rbac.service;

import cn.cnwair.mes.security.rbac.dto.UserCondition;
import cn.cnwair.mes.security.rbac.dto.UserInfo;
import cn.cnwair.mes.security.rbac.dto.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户服务
 */
public interface UserService {
    /**
     * 创建管理员
     * @param UserInfo
     * @return
     */
    UserInfo create(UserInfo UserInfo);
    /**
     * 修改管理员
     * @param UserInfo
     * @return
     */
    UserInfo update(UserInfo UserInfo);
    /**
     * 删除管理员
     * @param id
     */
    void delete(Long id);
    /**
     * 获取管理员详细信息
     * @param id
     * @return
     */
    UserInfo getInfo(Long id);
    /**
     * 分页查询管理员
     * @param condition
     * @return
     */
    Page<UserInfo> query(UserCondition condition, Pageable pageable);
}
