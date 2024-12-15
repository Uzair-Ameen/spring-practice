package com.practice.springpractice.bootstrap;

import com.practice.springpractice.entities.Role;
import com.practice.springpractice.enums.RoleEnum;
import com.practice.springpractice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.onLoad();
    }

    private void onLoad() {
        List<Role> roles = roleRepository.findAll();

        RoleEnum[] roleEnums = new RoleEnum[] { RoleEnum.ADMIN, RoleEnum.USER, RoleEnum.SUPER_ADMIN };
        Map<RoleEnum, String> descriptionMap = Map.of(
                RoleEnum.ADMIN, "Admin Role",
                RoleEnum.USER, "User Role",
                RoleEnum.SUPER_ADMIN, "Super Admin Role"
        );

        Arrays.stream(roleEnums)
                .filter(roleEnum -> roles.stream().noneMatch(role -> role.getName() == roleEnum))
                .forEach(roleEnum -> {
                    Role role = new Role(roleEnum, descriptionMap.get(roleEnum));

                    roleRepository.save(role);
                });
    }
}
