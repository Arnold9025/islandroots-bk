package com.islandroots.bk.modules.blog.service.impl;

import com.islandroots.bk.modules.blog.entity.Blog;
import com.islandroots.bk.modules.blog.repository.BlogRepository;
import com.islandroots.bk.modules.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public Blog createBlog(Blog blog) {
        if (blog.getPublishedAt() == null) {
            blog.setPublishedAt(LocalDateTime.now());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlogById(UUID id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog updateBlog(UUID id, Blog blogDetails) {
        Blog blog = getBlogById(id);
        blog.setTitle(blogDetails.getTitle());
        blog.setContent(blogDetails.getContent());
        blog.setImageUrl(blogDetails.getImageUrl());
        blog.setAuthor(blogDetails.getAuthor());
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(UUID id) {
        Blog blog = getBlogById(id);
        blogRepository.delete(blog);
    }
}
