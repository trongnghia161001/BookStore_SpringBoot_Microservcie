package com.bookstore.adminsservice.query.projection;

import com.bookstore.adminsservice.command.data.Admins;
import com.bookstore.adminsservice.command.data.AdminsRepository;
import com.bookstore.adminsservice.query.model.AdminsResponseModel;
import com.bookstore.adminsservice.query.queries.GetAdminByIdQuery;
import com.bookstore.adminsservice.query.queries.GetAllAdminsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AdminsProjection {

    @Autowired
    private AdminsRepository adminRepository;

    @QueryHandler
    public List<AdminsResponseModel> handler(GetAllAdminsQuery getAllBookQuery) {
        List<AdminsResponseModel> list = new ArrayList<>();
        List<Admins> bookList = adminRepository.findAll();
        bookList.forEach(book -> {
            AdminsResponseModel model = new AdminsResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public AdminsResponseModel handler(GetAdminByIdQuery getAdminByIdQuery) {
        AdminsResponseModel adminsResponseModel = new AdminsResponseModel();
        Optional<Admins> admins = adminRepository.findById(getAdminByIdQuery.getId());
        if (admins.isPresent()) {
            BeanUtils.copyProperties(admins.get(), adminsResponseModel);
        }
        return adminsResponseModel;
    }
}
