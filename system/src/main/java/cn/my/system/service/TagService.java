package cn.my.system.service;

import cn.my.system.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag update(Long id, Tag type);

    void deleteTag(Long id);

    Tag getTagByName(String name);
}
