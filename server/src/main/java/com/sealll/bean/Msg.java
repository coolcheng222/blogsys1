package com.sealll.bean;

/**
 * @author sealll
 * @time 2021/3/30 14:57
 */
public class Msg {
    // 0 成功
    // 1 失败,message包含失败信息
    // 2 表示按照message跳转
    private Integer errno;
    private String message;
    private Object extend;

    public static Msg success(String message){
        Msg msg = new Msg();
        msg.setErrno(0);
        msg.setMessage(message);
        return msg;
    }
    public static Msg fail(String message){
        Msg msg = new Msg();
        msg.setErrno(1);
        msg.setMessage(message);
        return msg;
    }
    public static Msg redirect(String url){
        Msg msg = new Msg();
        msg.setErrno(2);
        msg.setMessage(url);
        return msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "errno=" + errno +
                ", message='" + message + '\'' +
                ", extend=" + extend +
                '}';
    }
}
