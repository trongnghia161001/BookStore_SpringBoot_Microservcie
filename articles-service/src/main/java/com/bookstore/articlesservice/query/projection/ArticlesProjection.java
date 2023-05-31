package com.bookstore.articlesservice.query.projection;

import com.bookstore.articlesservice.command.data.Articles;
import com.bookstore.articlesservice.command.data.ArticlesRepository;
import com.bookstore.articlesservice.query.model.ArticlesResponseModel;
import com.bookstore.articlesservice.query.queries.GetAllArticlesByDesQuery;
import com.bookstore.articlesservice.query.queries.GetAllArticlesByNameQuery;
import com.bookstore.articlesservice.query.queries.GetAllArticlesQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticlesProjection {

    @Autowired
    private ArticlesRepository repository;

    @QueryHandler
    public List<ArticlesResponseModel> handler(GetAllArticlesQuery getAll) {
        List<ArticlesResponseModel> list = new ArrayList<>();
        List<Articles> List = repository.findAll();
        List.forEach(book -> {
            ArticlesResponseModel model = new ArticlesResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<ArticlesResponseModel> handler(GetAllArticlesByNameQuery getAllArticlesByNameQuery) {
        List<ArticlesResponseModel> list = new ArrayList<>();
        List<Articles> List = repository.findByNameContainingIgnoreCase(getAllArticlesByNameQuery.getName());
        List.forEach(book -> {
            ArticlesResponseModel model = new ArticlesResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<ArticlesResponseModel> handler(GetAllArticlesByDesQuery getAllArticlesByDesQuery) {
        List<ArticlesResponseModel> list = new ArrayList<>();
        List<Articles> List = repository.findByDescriptionContainingIgnoreCase(getAllArticlesByDesQuery.getDescription());
        List.forEach(book -> {
            ArticlesResponseModel model = new ArticlesResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }
}
