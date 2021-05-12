package com.sealll.mapper;

import com.sealll.application.fav.bean.Fav;
import com.sealll.application.fav.bean.Faved;
import com.sealll.application.fav.bean.Faving;
import com.sealll.application.post.bean.Post;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sealll
 * @time 2021/5/11 21:13
 */
public interface FavMapper {
    public Fav getUserFav(String uid);
    public Fav getUserFavCount(String uid);
    public Faved getPostFaved(String pid);
    public Faved getPostFavedCount(String pid);

    public List<Faved> getPostFavedCountList();

    public boolean addFav(@Param("uid")String uid,@Param("pid")String pid);
    public boolean deleteFav(@Param("uid")String uid,@Param("pid") String pid);

    @MapKey("pid")
    public Map<String, Faving> checkFav(@Param("uid")String uid, @Param("pids") List<String> pids);
}
