package com.immortaltrial.game.privilege.repository;

import com.immortaltrial.game.privilege.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    @Override
    void delete(@NonNull Privilege privilege);
}
