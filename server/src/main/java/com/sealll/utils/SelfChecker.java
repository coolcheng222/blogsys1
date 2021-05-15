package com.sealll.utils;

import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;

/**
 * @author sealll
 * @time 2021/5/15 15:53
 */
public class SelfChecker {
    public static boolean check(String myuid){
        Object attribute = SecurityUtils.getSubject().getSession().getAttribute(ParameterConstants.UID_SESSION_KEY);
        if(attribute == null){
            return false;
        }else{
            return myuid.equals(attribute);
        }
    }
}
