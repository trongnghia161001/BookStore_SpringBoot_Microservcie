package com.bookstore.attributesservice.query.projection;

import com.bookstore.attributesservice.command.data.Attributes;
import com.bookstore.attributesservice.command.data.AttributesRepository;
import com.bookstore.attributesservice.query.model.AttributesResponseModel;
import com.bookstore.attributesservice.query.queries.GetAllAttributesByCategoryQuery;
import com.bookstore.attributesservice.query.queries.GetAllAttributesByNameQuery;
import com.bookstore.attributesservice.query.queries.GetAllAttributesQuery;
import com.bookstore.commonservice.model.CategoriesResponseCommonModel;
import com.bookstore.commonservice.query.GetCategoriesByNameQuery;
import com.bookstore.commonservice.query.GetDetailsCategoriesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.ResponseCache;
import java.util.ArrayList;
import java.util.List;

@Component
public class AttributesProjection {

    @Autowired
    private AttributesRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<AttributesResponseModel> handler(GetAllAttributesQuery getAll) {
        List<AttributesResponseModel> list = new ArrayList<>();
        List<Attributes> List = repository.findAll();
        List.forEach(book -> {
            AttributesResponseModel model = new AttributesResponseModel();
            GetDetailsCategoriesQuery getDetailsCategoriesQuery = new GetDetailsCategoriesQuery(book.getCategoryId());
            CategoriesResponseCommonModel categoriesResponseCommonModel = queryGateway.query(getDetailsCategoriesQuery,
                    ResponseTypes.instanceOf(CategoriesResponseCommonModel.class)).join();
            BeanUtils.copyProperties(book, model);
            model.setCategoryName(categoriesResponseCommonModel.getName());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<AttributesResponseModel> handler(GetAllAttributesByNameQuery getAllAttributesByNameQuery) {
        List<AttributesResponseModel> list = new ArrayList<>();
        List<Attributes> List = repository.findByNameContainingIgnoreCase(getAllAttributesByNameQuery.getName());
        List.forEach(book -> {
            AttributesResponseModel model = new AttributesResponseModel();
            GetDetailsCategoriesQuery getDetailsCategoriesQuery = new GetDetailsCategoriesQuery(book.getCategoryId());
            CategoriesResponseCommonModel categoriesResponseCommonModel = queryGateway.query(getDetailsCategoriesQuery,
                    ResponseTypes.instanceOf(CategoriesResponseCommonModel.class)).join();
            BeanUtils.copyProperties(book, model);
            model.setCategoryName(categoriesResponseCommonModel.getName());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<AttributesResponseModel> handler(GetAllAttributesByCategoryQuery getAllAttributesByCategoryQuery) {
        List<AttributesResponseModel> list = new ArrayList<>();
        GetCategoriesByNameQuery getCategoriesByNameQuery = new GetCategoriesByNameQuery(getAllAttributesByCategoryQuery.getCategory());
        List<CategoriesResponseCommonModel> categoriesResponseCommonModels = queryGateway.query(getCategoriesByNameQuery,
                ResponseTypes.multipleInstancesOf(CategoriesResponseCommonModel.class)).join();
        if (categoriesResponseCommonModels.size() > 0) {
            categoriesResponseCommonModels.forEach(item -> {
                List<Attributes> attributes = repository.findByCategoryId(item.getId());
                attributes.forEach(book -> {
                    AttributesResponseModel attributesResponseModel = new AttributesResponseModel();
                    BeanUtils.copyProperties(book, attributesResponseModel);
                    attributesResponseModel.setCategoryName(item.getName());
                    list.add(attributesResponseModel);
                });
            });
        }
        return list;
    }
}
