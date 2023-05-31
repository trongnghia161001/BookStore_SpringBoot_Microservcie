package com.bookstore.carrierservice.query.projection;

import com.bookstore.carrierservice.command.data.Carrier;
import com.bookstore.carrierservice.command.data.CarrierRepository;
import com.bookstore.carrierservice.query.model.CarrierResponseModel;
import com.bookstore.carrierservice.query.queries.GetAllCarrierByAddressQuery;
import com.bookstore.carrierservice.query.queries.GetAllCarrierByNameQuery;
import com.bookstore.carrierservice.query.queries.GetAllCarrierByPhoneQuery;
import com.bookstore.carrierservice.query.queries.GetAllCarrierQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarrierProjection {

    @Autowired
    private CarrierRepository repository;

    @QueryHandler
    public List<CarrierResponseModel> handler(GetAllCarrierQuery getAll) {
        List<CarrierResponseModel> list = new ArrayList<>();
        List<Carrier> List = repository.findAll();
        List.forEach(book -> {
            CarrierResponseModel model = new CarrierResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<CarrierResponseModel> handler(GetAllCarrierByNameQuery getAll) {
        List<CarrierResponseModel> list = new ArrayList<>();
        List<Carrier> List = repository.findByNameContainingIgnoreCase(getAll.getName());
        List.forEach(book -> {
            CarrierResponseModel model = new CarrierResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<CarrierResponseModel> handler(GetAllCarrierByPhoneQuery getAll) {
        List<CarrierResponseModel> list = new ArrayList<>();
        List<Carrier> List = repository.findByPhoneContainingIgnoreCase(getAll.getPhone());
        List.forEach(book -> {
            CarrierResponseModel model = new CarrierResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<CarrierResponseModel> handler(GetAllCarrierByAddressQuery getAll) {
        List<CarrierResponseModel> list = new ArrayList<>();
        List<Carrier> List = repository.findByAddressContainingIgnoreCase(getAll.getAddress());
        List.forEach(book -> {
            CarrierResponseModel model = new CarrierResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

}
