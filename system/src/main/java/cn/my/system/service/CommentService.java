package cn.my.system.service;

import cn.my.system.entity.Comment;

import java.util.List;

public interface CommentService {
    //通过获取博客ID获取下面的评论列表
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
