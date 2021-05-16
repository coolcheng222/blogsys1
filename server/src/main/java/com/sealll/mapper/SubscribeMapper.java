package com.sealll.mapper;

import com.sealll.application.sub.bean.Subscribe;
import org.apache.ibatis.annotations.Param;

/**
 * @author sealll
 * @time 2021/5/16 12:22
 */
public interface SubscribeMapper {
    public boolean addSub(@Param("subber")String subber,@Param("subbed") String subbed);
    public boolean deleteSub(@Param("subber")String subber,@Param("subbed") String subbed);
    public boolean checkSub(@Param("subber")String subber,@Param("subbed") String subbed);
    public Subscribe getSubing(String uid);
    public Subscribe getFans(String uid);
}
