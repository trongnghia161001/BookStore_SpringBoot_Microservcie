package com.bookstore.addressservicee.command.events;

import com.bookstore.addressservicee.command.data.Address;
import com.bookstore.addressservicee.command.data.AddressRepository;
import com.bookstore.commonservice.event.AddressUpdateByUserEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressEventsHandler {

    @Autowired
    private AddressRepository adminRepository;

    @EventHandler
    public void on(AddressCreateEvent event) {
        Address admin = new Address();
        BeanUtils.copyProperties(event, admin);
        adminRepository.save(admin);
    }

    @EventHandler
    public void on(AddressUpdateEvent event) {
        Address admin = adminRepository.getReferenceById(event.getId());
        BeanUtils.copyProperties(event, admin);
        adminRepository.save(admin);
    }

    @EventHandler
    public void on(AddressDeleteEvent event) {
        adminRepository.deleteById(event.getId());
    }

    @EventHandler
    public void on(AddressUpdateByUserEvent event) {
        Address address = adminRepository.getReferenceById(event.getId());
        if (address != null) {
            address.setAddressLine1(event.getAddressLine1());
            adminRepository.save(address);
        }
    }
}
