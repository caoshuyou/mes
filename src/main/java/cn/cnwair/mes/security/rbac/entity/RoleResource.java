package cn.cnwair.mes.security.rbac.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class RoleResource implements Serializable {
    /**
     * 数据库表主键
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;
    /**
     * 角色
     */
    @ManyToOne
    private Role role;
    /**
     * 资源
     */
    @ManyToOne
    private Resource resource;
    /**
     * @return the id
     */
}
