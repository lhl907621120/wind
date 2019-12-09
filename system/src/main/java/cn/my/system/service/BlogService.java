package cn.my.system.service;

import cn.my.system.entity.Blog;
import cn.my.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog saveBlog(Blog blog);

    void deleteBlog(Long id);

    Blog updateBlog(Long id, Blog blog);

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);


}
