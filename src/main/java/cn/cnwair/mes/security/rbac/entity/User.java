package cn.cnwair.mes.security.rbac.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

@Data
@Entity
public class User implements UserDetails, Serializable {
    //数据主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    private String username;
    private String password;
    private Boolean locked;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;

    private Boolean enabled;

    public User() {
    }

    public User(String username, String password, Boolean locked, Date expired, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.locked = locked;
        this.expired = expired;
        this.enabled = enabled;
    }


    /**
     * 用户的所有角色
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<RoleUser> roles = new HashSet<>();
    /**
     * 用户有权访问的所有url，不持久化到数据库
     */
    @Transient
    private Set<String> urls = new HashSet<>();
    /**
     * 用户有权的所有资源id，不持久化到数据库
     */
    @Transient
    private Set<Long> resourceIds = new HashSet<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return expired.after(new Date());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Set<Long> getAllResourceIds() {
        init(resourceIds);
        forEachResource(resource -> resourceIds.add(resource.getId()));
        return resourceIds;
    }
    public Set<String> getUrls() {
        init(urls);
        forEachResource(resource -> urls.addAll(resource.getUrls()));
        return urls;
    }

    private void init(Set<?> data) {
        if (CollectionUtils.isEmpty(data)) {
            if (data == null) {
                data = new HashSet<>();
            }
        }
    }
    private void forEachResource(Consumer<Resource> consumer) {
        for (RoleUser role : roles) {
            for (RoleResource resource : role.getRole().getResources()) {
                consumer.accept(resource.getResource());
            }
        }
    }
}
