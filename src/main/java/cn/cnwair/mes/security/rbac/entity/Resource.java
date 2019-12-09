package cn.cnwair.mes.security.rbac.entity;

import cn.cnwair.mes.security.rbac.dto.ResourceInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Resource implements Serializable {
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
     * 资源名称，如xx菜单，xx按钮
     */
    private String name;
    /**
     * 资源链接
     */
    private String link;
    /**
     * 图标
     */
    private String icon;
    /**
     * 资源类型
     */
    @Enumerated(EnumType.STRING)
    private ResourceType type;
    /**
     * 实际需要控制权限的url
     */
    @ElementCollection
    private Set<String> urls;
    /**
     * 父资源
     */
    @ManyToOne
    private Resource parent;
    /**
     * 子资源
     */
    @OneToMany(mappedBy = "parent")
    @OrderBy("sort ASC")
    private List<Resource> childs = new ArrayList<>();


    public Resource(String name, ResourceType type, Set<String> urls) {
        this.name = name;
        this.type = type;
        this.urls = urls;
    }

    public Resource() {
    }

    public void addChild(Resource child) {
        childs.add(child);
        child.setParent(this);
    }
    /**
     * 序号
     */
    private int sort;

    public ResourceInfo toTree(User user) {
        ResourceInfo result = new ResourceInfo();
        BeanUtils.copyProperties(this, result);
        Set<Long> resourceIds = user.getAllResourceIds();

        List<ResourceInfo> children = new ArrayList<ResourceInfo>();
        for (Resource child : getChilds()) {
            if(StringUtils.equals(user.getUsername(), "admin") ||
                    resourceIds.contains(child.getId())){
                children.add(child.toTree(user));
            }
        }
        result.setChildren(children);
        return result;
    }
}
