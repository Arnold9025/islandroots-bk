package com.islandroots.bk.modules.blog.service;

import com.islandroots.bk.modules.blog.entity.Blog;

import java.util.List;
import java.util.UUID;

public interface BlogService {
    Blog createBlog(Blog blog);
    Blog getBlogById(UUID id);
    List<Blog> getAllBlogs();
    Blog updateBlog(UUID id, Blog blogDetails);
    void deleteBlog(UUID id);
}
