package com.bookstore.categoriesservice.query.projection;

import com.bookstore.categoriesservice.command.data.Categories;
import com.bookstore.categoriesservice.command.data.CategoriesRepository;
import com.bookstore.categoriesservice.query.model.CategoriesResponseModel;
import com.bookstore.categoriesservice.query.queries.GetAllCategoriesByDesQuery;
import com.bookstore.categoriesservice.query.queries.GetAllCategoriesByNameQuery;
import com.bookstore.categoriesservice.query.queries.GetAllCategoriesQuery;
import com.bookstore.commonservice.model.AuthorResponseCommonModel;
import com.bookstore.commonservice.model.CategoriesResponseCommonModel;
import com.bookstore.commonservice.query.GetCategoriesByNameQuery;
import com.bookstore.commonservice.query.GetDetailsCategoriesQuery;
import com.bookstore.commonservice.query.GetProductByAuthorQuery;
import com.bookstore.commonservice.query.GetProductByCategoryQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CategoriesProjection {

    @Autowired
    private CategoriesRepository repository;

    @QueryHandler
    public List<CategoriesResponseModel> handler(GetAllCategoriesQuery getAll) {
        List<CategoriesResponseModel> list = new ArrayList<>();
        List<Categories> List = repository.findAll();
        List.forEach(book -> {
            CategoriesResponseModel model = new CategoriesResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<CategoriesResponseModel> handler(GetAllCategoriesByNameQuery getAll) {
        List<CategoriesResponseModel> list = new ArrayList<>();
        List<Categories> List = repository.findByNameContainingIgnoreCase(getAll.getName());
        List.forEach(book -> {
            CategoriesResponseModel model = new CategoriesResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

//    @QueryHandler
//    public List<CategoriesResponseModel> handler(GetAllCategoriesByDesQuery getAll) {
//        List<CategoriesResponseModel> list = new ArrayList<>();
//        List<Categories> List = repository.findByDescriptionContainingIgnoreCase(getAll.getDescription());
//        List.forEach(book -> {
//            CategoriesResponseModel model = new CategoriesResponseModel();
//            BeanUtils.copyProperties(book, model);
//            list.add(model);
//        });
//        return list;
//    }



    @QueryHandler
    public CategoriesResponseCommonModel handler(GetDetailsCategoriesQuery getAll) {
        CategoriesResponseCommonModel model = new CategoriesResponseCommonModel();
        Optional<Categories> categories = repository.findById(getAll.getId());
        if (categories.isPresent()) {
            BeanUtils.copyProperties(categories.get(), model);
        }

        return model;
    }

    @QueryHandler
    public List<CategoriesResponseCommonModel> handler(GetCategoriesByNameQuery getAll) {
        List<CategoriesResponseCommonModel> list = new ArrayList<>();
        List<Categories> List = repository.findByNameContainingIgnoreCase(getAll.getName());
        List.forEach(book -> {
            CategoriesResponseCommonModel model = new CategoriesResponseCommonModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public CategoriesResponseCommonModel getAuthorById(GetProductByCategoryQuery getProductByCategoryQuery) {
        CategoriesResponseCommonModel categoriesResponseCommonModel = new CategoriesResponseCommonModel();
        Optional<Categories> categories = repository.findById(getProductByCategoryQuery.getId());
        if (categories.isPresent()) {
            BeanUtils.copyProperties(categories.get(), categoriesResponseCommonModel);
            return categoriesResponseCommonModel;
        } else {
            return null;
        }
    }
}
