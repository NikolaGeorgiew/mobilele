package org.softuni.mobilele.model.entity;


import jakarta.persistence.*;
import org.softuni.mobilele.model.enums.UserRoleEnum;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column
    private UserRoleEnum role;

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRole setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
