package ru.ebanievolki.kataplusbot.model;

import javax.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {
    @Id
    private long id;
    @Column
    private String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
