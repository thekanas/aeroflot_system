package by.stolybko.database.entity.enam;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}
