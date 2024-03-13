package io.github.mat3e.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class KeycloakConfiguration {
    private String resourceId;
    private String principalAttribute;

    String getResourceId() {
        return resourceId;
    }

    void setResourceId(final String resourceId) {
        this.resourceId = resourceId;
    }

    String getPrincipalAttribute() {
        return principalAttribute;
    }

    void setPrincipalAttribute(final String principalAttribute) {
        this.principalAttribute = principalAttribute;
    }
}
