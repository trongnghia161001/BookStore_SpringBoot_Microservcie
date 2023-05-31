package com.bookstore.commentsservice.query.controller;

import com.bookstore.commentsservice.query.model.CommentsResponseModel;
import com.bookstore.commentsservice.query.queries.GetAllCommentsByContentQuery;
import com.bookstore.commentsservice.query.queries.GetAllCommentsByNameQuery;
import com.bookstore.commentsservice.query.queries.GetAllCommentsQuery;
import com.bookstore.commonservice.query.GetDetailsUserQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<CommentsResponseModel> getAll() {
        GetAllCommentsQuery getAllBookQuery = new GetAllCommentsQuery();
        List<CommentsResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(CommentsResponseModel.class)).join();
        return list;
    }

    @GetMapping("/name")
    public List<CommentsResponseModel> getAllByName(@RequestParam(required = false) String name) {
        GetAllCommentsByNameQuery getAllCommentsByNameQuery = new GetAllCommentsByNameQuery(name);
        List<CommentsResponseModel> list = queryGateway.query(getAllCommentsByNameQuery,
                ResponseTypes.multipleInstancesOf(CommentsResponseModel.class)).join();
        return list;
    }

    @GetMapping("/content")
    public List<CommentsResponseModel> getAllByContent(@RequestParam(required = false) String content) {
        GetAllCommentsByContentQuery getAllCommentsByContentQuery = new GetAllCommentsByContentQuery(content);
        List<CommentsResponseModel> list = queryGateway.query(getAllCommentsByContentQuery,
                ResponseTypes.multipleInstancesOf(CommentsResponseModel.class)).join();
        return list;
    }


}

//tim kiem: name, content