package cn.cnwair.mes.security.rbac.service.impl;

import cn.cnwair.mes.security.rbac.dto.UserCondition;
import cn.cnwair.mes.security.rbac.dto.UserInfo;
import cn.cnwair.mes.security.rbac.entity.RoleUser;
import cn.cnwair.mes.security.rbac.entity.User;
import cn.cnwair.mes.security.rbac.repository.RoleRepository;
import cn.cnwair.mes.security.rbac.repository.RoleUserRepository;
import cn.cnwair.mes.security.rbac.repository.UserRepository;
import cn.cnwair.mes.security.rbac.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserInfo create(UserInfo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("123456"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR,1);
        user.setExpired(cal.getTime());
        user.setLocked(false);
        createRoleUser(userInfo,user);
        return userInfo;
    }

    @Override
    public UserInfo update(UserInfo userInfo) {
        User user = userRepository.getOne(userInfo.getId());
        BeanUtils.copyProperties(userInfo,user);
        createRoleUser(userInfo,user);
        return userInfo;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserInfo getInfo(Long id) {
        User user = userRepository.getOne(id);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        return userInfo;
    }

    @Override
    public Page<UserInfo> query(UserCondition condition, Pageable pageable) {
        return null;
    }
    /**
     * 创建角色用户关系数据。
     * @param userInfo
     * @param user
     */
    private void createRoleUser(UserInfo userInfo, User user) {
        if(CollectionUtils.isNotEmpty(user.getRoles())){
            roleUserRepository.deleteAll(user.getRoles());
        }
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(roleRepository.getOne(userInfo.getRoleId()));
        roleUser.setUser(user);
        roleUserRepository.save(roleUser);
    }
}
