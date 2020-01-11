package cn.my.system.service;

import cn.my.system.entity.Blog;
import cn.my.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    //保存博客
    Blog saveBlog(Blog blog);
    //删除博客
    void deleteBlog(Long id);
    //根据ID来更新博客
    Blog updateBlog(Long id, Blog blog);
    //通过ID查询博客
    Blog getBlog(Long id);
    //分页查询博客
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);
    //通过标题查询博客
    Blog getBlogByTitle(String title);
}
