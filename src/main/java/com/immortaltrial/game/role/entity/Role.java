package com.immortaltrial.game.role.entity;

import com.immortaltrial.game.privilege.entity.Privilege;
import com.immortaltrial.game.user.entity.User;
import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}
