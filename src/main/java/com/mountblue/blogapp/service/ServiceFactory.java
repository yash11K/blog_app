package com.mountblue.blogapp.service;

public interface ServiceFactory {
    PostService getPostService();

    TagService getTagService();

    PostTagService getPostTagService();

    CommentService getCommentService();

    UserService getUserService();

    SearchService getSearchService();

    FilterService getFilterService();

}
