package com.sealll.application.tag.service;

import com.sealll.application.tag.bean.Tag;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/9 17:04
 */
public interface TagService {
    public boolean addTag(Tag tag);
    public boolean updateTag(Tag tag);
    public Tag getPostCount(String tid);
    public List<Tag> orderByPostCount(int n);
    public List<Tag> selectPostCount();
    public Tag selectPostByTid(String tid, Integer page);
    public List<Tag> searchByTagname(String tagname);

    boolean deleteTag(Tag tag);
}
