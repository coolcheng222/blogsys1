package com.sealll.mapper;

import com.sealll.application.tag.bean.Tag;
import com.sealll.application.tag.bean.TagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagMapper {
    long countByExample(TagExample example);
    int deleteByExample(TagExample example);
    int deleteByPrimaryKey(String tag);
    int insert(Tag record);
    int insertSelective(Tag record);
    List<Tag> selectByExample(TagExample example);
    Tag selectByPrimaryKey(String tag);
    int updateByExampleSelective(@Param("record") Tag record, @Param("example") TagExample example);
    int updateByExample(@Param("record") Tag record, @Param("example") TagExample example);
    int updateByPrimaryKeySelective(Tag record);
    int updateByPrimaryKey(Tag record);

    List<Tag> orderByPostCount(int n);
    List<Tag> selectPostCount();
    Tag selectPostCountByTid(String tid);
}