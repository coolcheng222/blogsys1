package com.sealll.bean;

/**
 * @author sealll
 * @time 2021/3/30 14:57
 */
public class Msg {
    // 0 成功
    // 1 失败,message包含失败信息
    // 2 表示按照message跳转
    // 3 需要登录
    // 4 无权限
    private Integer errno;
    private String message;
    private Object extend;

    public static Msg success(String message){
        Msg msg = new Msg();
        msg.setErrno(0);
        msg.message(message);
        return msg;
    }
    public static Msg fail(String message){
        Msg msg = new Msg();
        msg.setErrno(1);
        msg.message(message);
        return msg;
    }
    public static Msg redirect(String url){
        Msg msg = new Msg();
        msg.setErrno(2);
        msg.message(url);
        return msg;
    }
    public static Msg auth(String message){
        Msg msg = new Msg();
        msg.setErrno(3);
        msg.message(message);
        return msg;
    }
    public static Msg permit(String message){
        Msg msg = new Msg();
        msg.setErrno(4);
        msg.message(message);
        return msg;
    }
    public Msg message(String message) {
        this.message = message;
        return this;
    }
    public Msg extend(Object extend) {
        this.extend = extend;
        return this;
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
