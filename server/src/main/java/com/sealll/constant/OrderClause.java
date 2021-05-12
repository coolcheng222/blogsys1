package com.sealll.constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/5/12 19:36
 */
public class OrderClause {
    public static Set<String> clauses = new HashSet<>();
    static {
        clauses.add("time");
        clauses.add("faved");
        clauses.add("reply");
    }
}
