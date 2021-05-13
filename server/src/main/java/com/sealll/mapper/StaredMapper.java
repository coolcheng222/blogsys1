package com.sealll.mapper;

import com.sealll.application.star.bean.Stared;

/**
 * @author sealll
 * @time 2021/5/13 16:19
 */
public interface StaredMapper {
    public boolean addStar(Stared stared);
    public boolean deleteStar(Stared stared);
}
