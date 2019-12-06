package cn.cnwair.mes.security.securityProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "mes.security")
public class SecurityProperties implements Serializable {

    private String loginprocessingurl ="/security/login";
}
