package cn.cnwair.mes.security.rbac.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
public class Role implements Serializable {
    /**
     * 数据库表主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;
    /**
     * 角色名称
     */
    @Column(length = 20, nullable = false)
    private String name;
    /**
     * 角色拥有权限的资源集合
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private Set<RoleResource> resources  = new HashSet<>();
    /**
     * 角色的用户集合
     */
    @OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
    private Set<RoleUser> users = new HashSet<>();

    @ManyToOne
    private Role parent;
    /**
     * 子资源
     */
    @OneToMany(mappedBy = "parent")
    @OrderBy("sort ASC")
    private List<Role> childs = new ArrayList<>();

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }
}
