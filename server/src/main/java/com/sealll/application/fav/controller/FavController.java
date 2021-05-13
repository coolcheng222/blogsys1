package com.sealll.application.fav.controller;

import com.sealll.application.fav.bean.Fav;
import com.sealll.application.fav.bean.Faved;
import com.sealll.application.fav.bean.Faving;
import com.sealll.application.fav.service.FavService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author sealll
 * @time 2021/5/13 11:56
 */
@RestController
@RequestMapping("/fav")
public class FavController {
    @Autowired
    private FavService favService;
    @PostMapping
    public Msg add(@RequestParam("uid") String uid,@RequestParam("pid") String pid){
        boolean b = favService.addFav(uid, pid);
        if(!b){
            return Msg.fail("添加失败");
        }else{
            return Msg.success("添加成功");
        }
    }
    @DeleteMapping
    public Msg delete(@RequestParam("uid") String uid,@RequestParam("pid") String pid){
        boolean b = favService.deleteFav(uid, pid);
        if(!b){
            return Msg.fail("删除失败");
        }else{
            return Msg.success("删除成功");
        }
    }
    @GetMapping("/check")
    public Msg checkUserFaving(@RequestParam("uid") String uid,@RequestParam("pid") String pid){
        Faving faving = favService.checkFav(uid, pid);
        return ResultHandler.handle(faving,Objects::isNull,"字段有误",faving);
    }

    @GetMapping("/post/list")
    public Msg getPostFavedCountList(){
        List<Faved> list = favService.getPostFavedCountList();
        if(list == null){
            return Msg.fail("未知错误,或者没有字段");
        }else{
            return Msg.success("").extend(list);
        }
    }

    @GetMapping("/user/{uid}")
    public Msg getUserFav(@PathVariable("uid")String uid,
                          @RequestParam(value="count",required = false,defaultValue = "false")Boolean count) {
        Fav userFav = null;
        if (count) {
            userFav = favService.getUserFavCount(uid);
        } else {
            userFav = favService.getUserFav(uid);
        }
        Msg msg = ResultHandler.handle(userFav, Objects::isNull, "用户不存在", userFav);
        return msg;
    }
    @GetMapping("/post/{pid}")
    public Msg getPostFav(@PathVariable("pid")String pid,
                          @RequestParam(value="count",required = false,defaultValue = "false")Boolean count) {
        Faved faved= null;
        if (count) {
            faved = favService.getPostFavedCount(pid);
        } else {
            faved = favService.getPostFaved(pid);
        }
        Msg msg = ResultHandler.handle(faved, Objects::isNull, "文章不存在", faved);
        return msg;
    }

}
