package cn.cnwair.mes.security.rbac.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserInfo {

    private Long id;
    /**
     * 角色id
     */
    @NotBlank(message = "角色id不能为空")
    private Long roleId;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;


}
