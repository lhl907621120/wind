package cn.my.system.service;

import cn.my.system.NoFoundException;
import cn.my.system.dao.BlogRepository;
import cn.my.system.entity.Blog;
import cn.my.system.entity.Type;
import cn.my.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    /*
    新增博客时初始化创建时间、更新时间，浏览次数
    修改博客时修改更新时间
    @Transactional管理事务控制增删改
     */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setView(0);
        } else {
            blog.setUpdateTime(new Date());

        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    /*
    b为原博客，blog为更新内容后的博客
    copyProperties(Object source, Object target)
    source为初始对象     target为目标对象
     */
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(null);
        if (b ==null){
            throw new NoFoundException("不存在该篇博客");
        }
        System.out.println(b);
        blog.setUpdateTime(new Date());
        blog.setView(b.getView());
        blog.setCreateTime(b.getCreateTime());
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[0]));
                return null;

            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Blog getBlogByTitle(String title) {
        return blogRepository.findByTitle(title);
    }
}
