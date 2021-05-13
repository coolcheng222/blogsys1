package com.sealll.utils;

import com.sealll.bean.Msg;

import java.util.function.Predicate;

/**
 * @author sealll
 * @time 2021/5/13 12:06
 */
public class ResultHandler {
    public static Msg handle(Object res,Predicate pre,String message,Object extend){
        if(pre.test(res)){
            return Msg.fail(message);
        }else{
            return Msg.success("").extend(extend);
        }
    }
}
