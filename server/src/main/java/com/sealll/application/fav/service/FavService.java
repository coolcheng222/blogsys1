package com.sealll.application.fav.service;

import com.sealll.application.fav.bean.Fav;
import com.sealll.application.fav.bean.Faved;
import com.sealll.application.fav.bean.Faving;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/13 11:41
 */
public interface FavService {
    public boolean addFav(String uid,String pid);
    public boolean deleteFav(String uid,String pid);

    public Fav getUserFav(String uid);
    public Fav getUserFavCount(String uid);
    public Faving checkFav(String uid,String pid);
    public Faved getPostFaved(String uid);
    public Faved getPostFavedCount(String pid);

    public List<Faved> getPostFavedCountList();
}
