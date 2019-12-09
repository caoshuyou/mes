package cn.cnwair.mes.security.rbac.service.impl;

import cn.cnwair.mes.security.rbac.entity.User;
import cn.cnwair.mes.security.rbac.service.RbacService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.thymeleaf.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import java.util.Set;
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission =false;
        if (principal instanceof User){
            if(StringUtils.equals(((User) principal).getUsername(), "admin")){
                hasPermission=true;
            }else {
                Set<String> urls = ((User) principal).getUrls();
                for (String url:urls) {
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }
        return hasPermission;
    }
}
