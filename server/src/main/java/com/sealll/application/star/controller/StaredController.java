package com.sealll.application.star.controller;

import com.sealll.application.star.bean.Stared;
import com.sealll.application.star.service.StaredService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @author sealll
 * @time 2021/5/13 21:02
 */
@RestController
@RequestMapping("/star")
public class StaredController {
    @Autowired
    private StaredService staredService;

    @PostMapping
    public Msg addStar(@RequestBody Stared stared){
        boolean b = staredService.addStar(stared);
        if(!b){
            return Msg.fail("收藏失败");
        }else{
            return Msg.success("收藏成功");
        }
    }
    @DeleteMapping
    public Msg deleteStar(@RequestBody Stared stared){
        boolean b = staredService.deleteStar(stared);
        if(!b){
            return Msg.fail("取消收藏失败");
        }else{
            return Msg.success("取消收藏成功");
        }
    }

}
