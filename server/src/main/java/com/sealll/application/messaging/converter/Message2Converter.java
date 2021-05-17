package com.sealll.application.messaging.converter;

import com.sealll.application.fav.bean.Fav;
import com.sealll.application.message.bean.Message2;
import com.sealll.application.reply.bean.Reply;
import com.sealll.application.role.bean.Role;

/**
 * @author sealll
 * @time 2021/5/17 10:51
 */
public interface Message2Converter {
    public Message2 convert(Reply reply);
    public Message2 convert(Role role);
    public Message2 convert(String subber,String subbed);
}
