package com.d.dao.ncuter.api;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by dao on 7/3/16.
 */
public interface ApiService {

    //多模式教学网登陆
//    @Headers({
//            "Cache-Control: max-age=640000",
//            "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
//            "Accept-Encoding:gzip, deflate",
//            "Accept-Language:en-US,en;q=0.8",
//            "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36"
//    })
    @FormUrlEncoded
    @POST("/eclass/index.php")
    Observable<String> multi_login(
            @Field("login") String login,
            @Field("password") String password,
            @Field("submitAuth") String submitAuth
    );


    //    课程简介获取
//    @Headers({
//            "Cache-Control: max-age=640000",
//            "Host:e.ncut.edu.cn",
//            "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
//            "Accept-Encoding:gzip, deflate",
//            "Accept-Language:en-US,en;q=0.8",
//            "Referer:http://e.ncut.edu.cn/eclass/",
//            "Upgrade-Insecure-Requests: 1",
//            "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36"
//    })
    @GET("/eclass/{address}")
    Observable<String> multi_getCourseSummaryMsg(@Path("address") String address);

    //课件课表获取
//    @Headers({
//            "Cache-Control: max-age=640000",
//            "Host:e.ncut.edu.cn",
//            "Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
//            "Accept-Encoding:gzip, deflate, sdch",
//            "Accept-Language:en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4",
//            "Referer:http://e.ncut.edu.cn/eclass/c_2016S_7088601cb70/index.php",
//            "Upgrade-Insecure-Requests: 1",
//            "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36"
//    })
    @GET("/eclass/eclass/{address}/{address2}")
    Observable<String> multi_getCourseWareMsg(@Path("address") String address,
                                              @Path("address2") String address2);

    @GET("/eclass/eclass/{address}")
    Observable<String> multi_getCourseWareMsg(@Path("address") String address
    );

    //教学信息网登陆
    @Headers({"Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
            "Accept-Encoding:gzip, deflate, sdch",
            "Accept-Language:en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4",
            "Cache-Control:max-age=0",
            "Connection:keep-alive",
            "Content-Type:application/x-www-form-urlencoded",
            "Proxy-Connection:keep-alive",
            "Host:jxxx.ncut.edu.cn",
            "Origin:http://jxxx.ncut.edu.cn",
            "Referer:http://jxxx.ncut.edu.cn/home.asp",
            "Upgrade-Insecure-Requests:1",
            "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36"
    })
//    /xs/index.asp
    @FormUrlEncoded
    @POST("/login.asp")
    Observable<String> teach_login(@Field("category") String category,
                                   @Field("uid") String uid,
                                   @Field("passwd") String passwd,
                                   @Field("Submit.x") int x,
                                   @Field("Submit.y") int y
    );

    @GET("/xs/grxx.asp")
    Observable<String> teach_query_person_info2(@Query("id") String id);

    //个人信息查询
    @FormUrlEncoded
    @POST("/xs/grxx.asp")
    Observable<String> teach_query_person_info(@Query("id") String id,
                                               @Field("r") String r
    );

    //查询成绩
//    /xs/cjkb.asp?id=5
    @FormUrlEncoded
    @POST("/xs/cjkb.asp")
    Observable<String> teach_query_person_score(@Query("id") String id,
                                                @Field("r") String r);

    //成绩绩点 /xs/cjkb.asp?id=6
    @FormUrlEncoded
    @POST("/xs/cjkb.asp")
    Observable<String> teach_query_person_gpa(@Query("id") String id,
                                                @Field("r") String r);

    //    个人课表 /xs/cjkb.asp?id=1
    @FormUrlEncoded
    @POST("/xs/cjkb.asp")
    Observable<String> teach_query_person_timetable(@Query("id") String id,
                                              @Field("r") String r);

    //    考试安排 /xs/cjkb.asp?id=3

    @FormUrlEncoded
    @POST("/xs/cjkb.asp")
    Observable<String> teach_query_person_exam_schedule(@Query("id") String id,
                                              @Field("r") String r);

}
