package cn.cnwair.mes.security.rbac.service;

import cn.cnwair.mes.security.rbac.dto.RoleInfo;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * rbac
 *
 *
 */
public interface RbacService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
