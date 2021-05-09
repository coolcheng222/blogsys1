package com.sealll.application.tag.service.impl;

import com.sealll.application.tag.bean.Tag;
import com.sealll.application.tag.bean.TagExample;
import com.sealll.application.tag.service.TagService;
import com.sealll.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/9 17:33
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public boolean addTag(Tag tag) {
        int insert = tagMapper.insert(tag);
        return insert != 0;
    }

    @Override
    public boolean updateTag(Tag tag) {
        int i = tagMapper.updateByPrimaryKey(tag);
        return i != 0;
    }

    @Override
    public Tag getPostCount(String tid) {
        Tag tag = tagMapper.selectPostCountByTid(tid);
        return tag;
    }

    @Override
    public List<Tag> orderByPostCount(int n) {
        List<Tag> tags = tagMapper.orderByPostCount(n);
        return tags;
    }

    @Override
    public List<Tag> selectPostCount() {
        List<Tag> tags = tagMapper.selectPostCount();
        return tags;
    }

    @Override
    public List<Tag> searchByTagname(String tagname) {
        tagname.replace("%","\\%");
        tagname.replace("_","\\_");
        tagname += '%';
        TagExample tagExample = new TagExample();
        TagExample.Criteria criteria = tagExample.createCriteria();
        TagExample.Criteria criteria1 = criteria.andTagnameLike(tagname);
        List<Tag> tags = tagMapper.selectByExample(tagExample);
        return tags;
    }

    @Override
    public boolean deleteTag(Tag tag) {
        int i = tagMapper.deleteByPrimaryKey(tag.getTag());
        return i != 0;
    }
}
