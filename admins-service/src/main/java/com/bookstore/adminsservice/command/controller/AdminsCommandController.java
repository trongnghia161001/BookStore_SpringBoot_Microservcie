package com.bookstore.adminsservice.command.controller;


import com.bookstore.adminsservice.command.command.CreateAdminsCommand;
import com.bookstore.adminsservice.command.command.DeleteAdminsCommand;
import com.bookstore.adminsservice.command.command.UpdateAdminsCommand;
import com.bookstore.adminsservice.command.model.AdminsRequestModel;
import com.bookstore.adminsservice.command.service.AdminsService;
import com.bookstore.adminsservice.query.model.AdminsResponseModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
//@CrossOrigin(origins = "http://localhost:3001")
public class AdminsCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private AdminsService adminsService;

    @PostMapping
    public String addAdmin(@RequestBody AdminsRequestModel model) {
        CreateAdminsCommand command = new CreateAdminsCommand();
        BeanUtils.copyProperties(model, command);
        command.setId(UUID.randomUUID().toString());
        commandGateway.sendAndWait(command);
        return "Add";
    }

    @PutMapping
    public String updateBook(@RequestBody AdminsRequestModel model) {
        UpdateAdminsCommand command = new UpdateAdminsCommand();
        BeanUtils.copyProperties(model, command);
        commandGateway.sendAndWait(command);
        return "Update";
    }

    @DeleteMapping("/{adminId}")
    public String deleteBook(@PathVariable String adminId) {
        DeleteAdminsCommand command = new DeleteAdminsCommand(adminId);
        commandGateway.sendAndWait(command);
        return "Delete";
    }

    @PostMapping("/login")
    public AdminsResponseModel login(@RequestBody AdminsRequestModel requestModel) {
        return adminsService.login(requestModel.getEmail(), requestModel.getPassword());
    }
}
