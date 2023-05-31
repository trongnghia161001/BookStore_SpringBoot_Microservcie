package com.bookstore.commentsservice.query.projection;

import com.bookstore.commentsservice.command.data.Comments;
import com.bookstore.commentsservice.command.data.CommentsRepository;
import com.bookstore.commentsservice.query.model.CommentsResponseModel;
import com.bookstore.commentsservice.query.queries.GetAllCommentsByContentQuery;
import com.bookstore.commentsservice.query.queries.GetAllCommentsByNameQuery;
import com.bookstore.commentsservice.query.queries.GetAllCommentsQuery;
import com.bookstore.commonservice.model.UserResponseCommonModel;
import com.bookstore.commonservice.query.GetCommentByUserNameQuery;
import com.bookstore.commonservice.query.GetDetailsUserQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentsProjection {

    @Autowired
    private CommentsRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<CommentsResponseModel> handler(GetAllCommentsQuery getAll) {
        List<CommentsResponseModel> list = new ArrayList<>();
        List<Comments> List = repository.findAll();
        List.forEach(book -> {
            CommentsResponseModel model = new CommentsResponseModel();
            GetDetailsUserQuery getDetailsUserQuery = new GetDetailsUserQuery(book.getUserId());
            UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailsUserQuery,
                    ResponseTypes.instanceOf(UserResponseCommonModel.class)).join();
            BeanUtils.copyProperties(book, model);
            model.setName(userResponseCommonModel.getLastName() + " " + userResponseCommonModel.getFirstName());
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<CommentsResponseModel> handler(GetAllCommentsByNameQuery getAll) {
        List<CommentsResponseModel> list = new ArrayList<>();
        GetCommentByUserNameQuery getCommentByUserNameQuery = new GetCommentByUserNameQuery(getAll.getName());
        List<UserResponseCommonModel> userResponseCommonModels = queryGateway.query(getCommentByUserNameQuery,
                ResponseTypes.multipleInstancesOf(UserResponseCommonModel.class)).join();
        if (userResponseCommonModels.size() > 0) {
            userResponseCommonModels.forEach(item -> {
                List<Comments> comments = repository.findByUserIdIsNotNull(item.getId());
                comments.forEach(comment -> {
                    CommentsResponseModel commentsResponseModel = new CommentsResponseModel();
                    BeanUtils.copyProperties(comment, commentsResponseModel);
                    list.add(commentsResponseModel);
                });
            });
        }
        return list;
    }

    @QueryHandler
    public List<CommentsResponseModel> handler(GetAllCommentsByContentQuery getAll) {
        List<CommentsResponseModel> list = new ArrayList<>();
        List<Comments> List = repository.findByContentContainingIgnoreCase(getAll.getContent());
        List.forEach(book -> {
            CommentsResponseModel model = new CommentsResponseModel();
            GetDetailsUserQuery getDetailsUserQuery = new GetDetailsUserQuery(book.getUserId());
            UserResponseCommonModel userResponseCommonModel = queryGateway.query(getDetailsUserQuery,
                    ResponseTypes.instanceOf(UserResponseCommonModel.class)).join();
            BeanUtils.copyProperties(book, model);
            model.setName(userResponseCommonModel.getLastName() + " " + userResponseCommonModel.getFirstName());
            list.add(model);
        });
        return list;
    }
}
