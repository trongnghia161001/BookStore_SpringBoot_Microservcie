package com.bookstore.adminsservice.command.events;

import com.bookstore.adminsservice.command.data.Admins;
import com.bookstore.adminsservice.command.data.AdminsRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminsEventsHandler {
    @Autowired
    private AdminsRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventHandler
    public void on(AdminsCreateEvent event) {
        Admins admin = new Admins();
        BeanUtils.copyProperties(event, admin);
        admin.setPassword(passwordEncoder.encode(event.getPassword()));
        admin.setRole("admin");
        adminRepository.save(admin);
    }

    @EventHandler
    public void on(AdminsUpdateEvent event) {
        Optional<Admins> admin = adminRepository.findById(event.getId());
        if (admin.isPresent()) {
            admin.get().setLastName(event.getLastName());
            admin.get().setFirstName(event.getFirstName());
            admin.get().setPhone(event.getPhone());
            admin.get().setAddress(event.getAddress());
            adminRepository.save(admin.get());
        }
    }

    @EventHandler
    public void on(AdminsDeleteEvent event) {
        adminRepository.deleteById(event.getId());
    }
}
