package com.centit.server.dingtalk.manager.po;/**
 * Created by li_hao on 2017/6/10.
 */

/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 钉钉开放平台接口的全局唯一票据
 * @Date : 2017-06-10 17:22
 **/
public class AccessToken {

    private String token;//获取到的凭证
    private int expiresIn;//凭证有效时间，单位：秒


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public int getExpiresIn() {
        return expiresIn;
    }
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
    @Override
    public String toString() {
        return "AccessToken [token=" + token + ", expiresIn=" + expiresIn + "]";
    }
}
