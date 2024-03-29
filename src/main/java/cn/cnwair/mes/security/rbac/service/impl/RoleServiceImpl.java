package cn.cnwair.mes.security.rbac.service.impl;

import cn.cnwair.mes.security.rbac.dto.RoleInfo;
import cn.cnwair.mes.security.rbac.entity.Role;
import cn.cnwair.mes.security.rbac.entity.RoleResource;
import cn.cnwair.mes.security.rbac.repository.ResourceRepository;
import cn.cnwair.mes.security.rbac.repository.RoleRepository;
import cn.cnwair.mes.security.rbac.repository.RoleResourceRepository;
import cn.cnwair.mes.security.rbac.repository.support.QueryResultConverter;
import cn.cnwair.mes.security.rbac.service.RoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;


    @Override
    public RoleInfo create(RoleInfo info) {
        Role role = new Role();
        BeanUtils.copyProperties(info, role);
        info.setId(roleRepository.save(role).getId());
        return info;
    }

    /* (non-Javadoc)
     * @see com.imooc.security.rbac.service.RoleService#update(com.imooc.security.rbac.dto.RoleInfo)
     */
    @Override
    public RoleInfo update(RoleInfo info) {
        Role role = roleRepository.getOne(info.getId());
        BeanUtils.copyProperties(info, role);
        return info;
    }


    @Override
    public void delete(Long id) {
        Role role = roleRepository.getOne(id);
        if(CollectionUtils.isNotEmpty(role.getUsers())){
            throw new RuntimeException("不能删除有下挂用户的角色");
        }
        roleRepository.deleteById(id);
    }

    @Override
    public RoleInfo getInfo(Long id) {
        Role role = roleRepository.getOne(id);
        RoleInfo info = new RoleInfo();
        BeanUtils.copyProperties(role, info);
        return info;
    }

    /* (non-Javadoc)
     * @see com.imooc.security.rbac.service.RoleService#findAll()
     */
    @Override
    public List<RoleInfo> findAll() {
        return QueryResultConverter.convert(roleRepository.findAll(), RoleInfo.class);
    }

    @Override
    public String[] getRoleResources(Long id) {
        Role role = roleRepository.getOne(id);
        Set<String> resourceIds = new HashSet<>();
        for (RoleResource resource : role.getResources()) {
            resourceIds.add(resource.getResource().getId().toString());
        }
        return resourceIds.toArray(new String[resourceIds.size()]);
    }


    @Override
    public void setRoleResources(Long roleId, String resourceIds) {
        resourceIds = StringUtils.removeEnd(resourceIds, ",");
        Role role = roleRepository.getOne(roleId);
        roleResourceRepository.deleteAll(role.getResources());
        String[] resourceIdArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(resourceIds, ",");
        for (String resourceId : resourceIdArray) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRole(role);
            roleResource.setResource(resourceRepository.getOne(new Long(resourceId)));
            roleResourceRepository.save(roleResource);
        }
    }

}
