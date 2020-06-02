package cn.my.system.service;

import cn.my.system.entity.Blog;
import cn.my.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //保存博客
    Blog saveBlog(Blog blog);

    //删除博客
    void deleteBlog(Long id);

    //根据ID来更新博客
    Blog updateBlog(Long id, Blog blog);

    //通过ID查询博客
    Blog getBlog(Long id);

    //获取并转换
    Blog getAndConvert(Long id);
    //分页查询博客
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

//    根据tagId分页查询博客
    Page<Blog> listBlog(Long tagId, Pageable pageable);

    //通过标题查询博客
    Blog getBlogByTitle(String title);

    //推荐博客列表
    List<Blog> listRecommendBlogTop(Integer size);

    //归档年份列表
    Map<String,List<Blog>> archiveBlog();

    //获得博客条数
    Long countBlog();
}
