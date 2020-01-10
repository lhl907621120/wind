package cn.my.system.service;

import cn.my.system.NoFoundException;
import cn.my.system.dao.AboutRepository;
import cn.my.system.entity.About;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AboutServiceImpl implements AboutService{

    @Autowired
    private AboutRepository aboutRepository;

    @Override
    @Transactional
    public About saveAbout(About about) {
        return aboutRepository.save(about);
    }

    @Override
    @Transactional
    public void deleteAbout(Long id) {
        aboutRepository.deleteById(id);
    }

    @Override
    @Transactional
    public About updateAbout(Long id, About about) {
        About a = aboutRepository.getOne(id);
        if (a ==null){
            throw new NoFoundException("不存在该管理员角色");
        }
        BeanUtils.copyProperties(about,a);
        return aboutRepository.save(a);
    }

    @Override
    public About getAbout(Long id) {
        return aboutRepository.getOne(id);
    }

    @Override
    public String getAvatar(Long id) {
        About avatarById = aboutRepository.getAvatarById(id);
        String avatar = avatarById.getAvatar();
        return avatar;
    }


}
