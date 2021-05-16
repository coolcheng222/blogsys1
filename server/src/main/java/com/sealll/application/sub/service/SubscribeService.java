package com.sealll.application.sub.service;

import com.sealll.application.sub.bean.Subscribe;
import org.apache.ibatis.annotations.Param;

/**
 * @author sealll
 * @time 2021/5/14 10:28
 */
public interface SubscribeService {
    public boolean addSub(String subber,  String subbed);
    public boolean deleteSub(String subber, String subbed);
    public boolean checkSub(String subber, String subbed);
    public Subscribe getSubing(String uid);
    public Subscribe getFans(String uid);
}
