package com.bookstore.addressservicee.query.projection;

import com.bookstore.addressservicee.command.data.Address;
import com.bookstore.addressservicee.command.data.AddressRepository;
import com.bookstore.addressservicee.query.model.AddressResponseModel;
import com.bookstore.addressservicee.query.queries.GetAllAddressByFirstnameQuery;
import com.bookstore.addressservicee.query.queries.GetAllAddressByPhoneQuery;
import com.bookstore.addressservicee.query.queries.GetAllAddressByProvinceQuery;
import com.bookstore.addressservicee.query.queries.GetAllAddressQuery;
import com.bookstore.commonservice.model.AddressResponseCommonModel;
import com.bookstore.commonservice.model.ProvinceResponseCommonModel;
import com.bookstore.commonservice.query.GetDetailsAddressQuery;
import com.bookstore.commonservice.query.GetDetailsProvinceQuery;
import com.bookstore.commonservice.query.GetProvinceByNameQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressProjection {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<AddressResponseModel> handler(GetAllAddressQuery getAllBookQuery) {
        List<AddressResponseModel> list = new ArrayList<>();
        List<Address> bookList = repository.findAll();
        bookList.forEach(book -> {
            AddressResponseModel model = new AddressResponseModel();
            GetDetailsProvinceQuery getDetailsAddressQuery
                    = new GetDetailsProvinceQuery(book.getProvinceId());
            ProvinceResponseCommonModel provinceResponseCommonModel = queryGateway.
                    query(getDetailsAddressQuery, ResponseTypes.instanceOf(ProvinceResponseCommonModel.class)).join();
            BeanUtils.copyProperties(book, model);
            model.setAddressLine1(book.getAddressLine1() + ", " + provinceResponseCommonModel.getName());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public AddressResponseCommonModel handler(GetDetailsAddressQuery getDetailsAddressQuery) {
        AddressResponseCommonModel model = new AddressResponseCommonModel();
        Address address = repository.findByUserId(getDetailsAddressQuery.getId());
        GetDetailsProvinceQuery getDetailsProvinceQuery
                = new GetDetailsProvinceQuery(address.getProvinceId());
        ProvinceResponseCommonModel provinceResponseCommonModel = queryGateway.
                query(getDetailsProvinceQuery, ResponseTypes.instanceOf(ProvinceResponseCommonModel.class)).join();
        BeanUtils.copyProperties(address, model);
        model.setAddressLine1(address.getAddressLine1() + ", " + provinceResponseCommonModel.getName());
        return model;
    }

    @QueryHandler
    public List<AddressResponseModel> handler(GetAllAddressByFirstnameQuery getAllAddressByFirstnameQuery) {
        List<AddressResponseModel> list = new ArrayList<>();
        List<Address> bookList = repository.findByFirstNameContainingIgnoreCase(getAllAddressByFirstnameQuery.getFirstName());
        bookList.forEach(book -> {
            AddressResponseModel model = new AddressResponseModel();
            GetDetailsProvinceQuery getDetailsAddressQuery
                    = new GetDetailsProvinceQuery(book.getProvinceId());
            ProvinceResponseCommonModel provinceResponseCommonModel = queryGateway.
                    query(getDetailsAddressQuery, ResponseTypes.instanceOf(ProvinceResponseCommonModel.class)).join();
            BeanUtils.copyProperties(book, model);
            model.setAddressLine1(book.getAddressLine1() + ", " + provinceResponseCommonModel.getName());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<AddressResponseModel> handler(GetAllAddressByPhoneQuery getAllAddressByPhoneQuery) {
        List<AddressResponseModel> list = new ArrayList<>();
        List<Address> bookList = repository.findByPhoneNumberContainingIgnoreCase(getAllAddressByPhoneQuery.getPhoneNumber());
        bookList.forEach(book -> {
            AddressResponseModel model = new AddressResponseModel();
            GetDetailsProvinceQuery getDetailsAddressQuery
                    = new GetDetailsProvinceQuery(book.getProvinceId());
            ProvinceResponseCommonModel provinceResponseCommonModel = queryGateway.
                    query(getDetailsAddressQuery, ResponseTypes.instanceOf(ProvinceResponseCommonModel.class)).join();
            BeanUtils.copyProperties(book, model);
            model.setAddressLine1(book.getAddressLine1() + ", " + provinceResponseCommonModel.getName());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<AddressResponseModel> handler(GetAllAddressByProvinceQuery getAllAddressByProvinceQuery) {
        List<AddressResponseModel> list = new ArrayList<>();
        GetProvinceByNameQuery getDetailsProvinceQuery
                = new GetProvinceByNameQuery(getAllAddressByProvinceQuery.getProvinceName());
        List<ProvinceResponseCommonModel> provinceResponseCommonModel = queryGateway.
                query(getDetailsProvinceQuery, ResponseTypes.multipleInstancesOf(ProvinceResponseCommonModel.class)).join();
        if (provinceResponseCommonModel.size() > 0) {
            provinceResponseCommonModel.forEach(item -> {
                List<Address> bookList = repository.findByProvinceId(item.getId());
                bookList.forEach(book -> {
                    AddressResponseModel model = new AddressResponseModel();
                    BeanUtils.copyProperties(book, model);
                    model.setAddressLine1(book.getAddressLine1() + ", " + item.getName());
                    list.add(model);
                });
            });
        }
        return list;
    }



}
