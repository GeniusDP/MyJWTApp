package com.example.demo.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private RoleValue name;

    public Role(RoleValue name) {
        this.name = name;
    }

}
