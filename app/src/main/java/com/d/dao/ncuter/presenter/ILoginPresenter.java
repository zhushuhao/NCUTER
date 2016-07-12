package com.d.dao.ncuter.presenter;

/**
 * Created by dao on 7/3/16.
 * 登陆
 */
public interface ILoginPresenter extends BasePresenter{

    /**
     * 登陆x
     * @param user
     * @param pass
     * @param flag 0:多模式教学网
     *             1:教学信息网
     */
    void login(String user,String pass,int flag);
}
