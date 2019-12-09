package cn.cnwair.mes.security.rbac.service.impl;

import cn.cnwair.mes.security.rbac.dto.ResourceInfo;
import cn.cnwair.mes.security.rbac.entity.Resource;
import cn.cnwair.mes.security.rbac.entity.User;
import cn.cnwair.mes.security.rbac.repository.ResourceRepository;
import cn.cnwair.mes.security.rbac.repository.UserRepository;
import cn.cnwair.mes.security.rbac.service.RbacService;
import cn.cnwair.mes.security.rbac.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResourceInfo getTree(Long userId) {
        User user = userRepository.getOne(userId);

        return resourceRepository.findByName("根节点").toTree(user);
    }

    @Override
    public ResourceInfo getInfo(Long id) {
        Resource resource = resourceRepository.getOne(id);
        ResourceInfo resourceInfo = new ResourceInfo();
        BeanUtils.copyProperties(resource, resourceInfo);
        return resourceInfo;
    }

    @Override
    public ResourceInfo create(ResourceInfo info) {
        Resource parent = resourceRepository.getOne(info.getParentId());
        if(parent == null){
            parent = resourceRepository.findByName("根节点");
        }
        Resource resource = new Resource();
        BeanUtils.copyProperties(info, resource);
        parent.addChild(resource);
        info.setId(resourceRepository.save(resource).getId());
        return info;
    }

    @Override
    public ResourceInfo update(ResourceInfo info) {
        Resource resource = resourceRepository.getOne(info.getId());
        BeanUtils.copyProperties(info, resource);
        return info;
    }

    @Override
    public void delete(Long id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public Long move(Long id, boolean up) {
        Resource resource = resourceRepository.getOne(id);
        int index = resource.getSort();
        List<Resource> childs = resource.getParent().getChilds();
        for (int i = 0; i < childs.size(); i++) {
            Resource current = childs.get(i);
            if(current.getId().equals(id)) {
                if(up){
                    if(i != 0) {
                        Resource pre = childs.get(i - 1);
                        resource.setSort(pre.getSort());
                        pre.setSort(index);
                        resourceRepository.save(pre);
                    }
                }else{
                    if(i != childs.size()-1) {
                        Resource next = childs.get(i + 1);
                        resource.setSort(next.getSort());
                        next.setSort(index);
                        resourceRepository.save(next);
                    }
                }
            }
        }
        resourceRepository.save(resource);
        return resource.getParent().getId();
    }
}
