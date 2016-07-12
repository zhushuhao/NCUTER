package com.d.dao.ncuter.utils;

import android.util.Log;

import com.d.dao.ncuter.bean.Course;
import com.d.dao.ncuter.bean.CourseSummary;
import com.d.dao.ncuter.bean.CourseWare;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dao on 7/11/16.
 */
public class StringUtils {

    //获取课程列表信息
    //正则截取课程信息
    public static List<Course> getCourseMsg(String msg){
        String temp1 = msg.substring(msg.indexOf("课程列表"));
        String temp2 = temp1.substring(0, temp1.indexOf("更新我的课程列表"));
        Pattern p = Pattern.compile("<a(.*)</a>");
        Matcher m = p.matcher(temp2);
        List<Course> list = new ArrayList<>();
        while (m.find()) {
            String str = m.group(0);
            String course_address = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
            String course_name = str.substring(str.indexOf(">") + 1, str.lastIndexOf("<"));
            Course course = new Course(course_address, course_name);
            list.add(course);
        }

        Pattern p2 = Pattern.compile("<small>(.*)</small>");
        Matcher m2 = p2.matcher(msg);
        int j = 0;
        while (m2.find()) {
            String str = m2.group(0);
            String temp = str.substring(str.indexOf(">") + 1, str.lastIndexOf("<"));
            String course_code = temp.substring(0, temp.indexOf("-")).trim();
            String teacher_name = temp.substring(temp.indexOf("-") + 1).trim();
            list.get(j).setCourse_code(course_code);
            list.get(j).setTeacher_name(teacher_name);
            j++;
        }
        //获取到有用信息,打印输出
//        Log.e("list", list.toString());
        return list;
    }

    //课程简介的12个12item标题与地址
    // GridView截取所有的12item标题与地址
    public static List<CourseSummary> getCourseSummaryMsg(String s) {
        List<CourseSummary> list = new ArrayList<>();

        //获取12个简介名称
        Pattern p = Pattern.compile("<font(.*)</font>");
        Matcher m = p.matcher(s);
        while (m.find()) {
            String str = m.group(0);
//                            <font color="##003399">课程信息</font>
            String title = str.substring(str.indexOf(">") + 1, str.lastIndexOf("<")).trim();
            CourseSummary courseSummary = new CourseSummary(title);
            list.add(courseSummary);
        }

        //获取点击的地址
        int index = 0;
        Pattern p2 = Pattern.compile("<a(.*)<img");
        Matcher m2 = p2.matcher(s);
        while (m2.find()) {
            String str = m2.group(0);
//            Log.e("str2", str);
//            <a href="../eclass/group/group.php"><img
//          需要的数据:"group/group.php"
            String temp1 = str.substring(str.indexOf("/") + 1);
            String temp2 =
                    temp1.substring(temp1.indexOf("/") + 1, temp1.lastIndexOf("\"")).trim();
            list.get(index).setAddress(temp2);
            index++;
        }
        return list;
    }


    //截取课件信息
    public static List<CourseWare> getCourseWareMsg(String s) {
        //获取课件大小与日期
        Pattern p = Pattern.compile("<small>(.*)</small>");
        Matcher m = p.matcher(s);
        int i = 0;
        List<CourseWare> list = new ArrayList<>();
        CourseWare courseWare = new CourseWare();
        while (m.find()) {
            String str = m.group(0);
//            <small>248 K</small>大小
//            <small>2016-03-01 13:19:57</small>日期
            String temp = str.substring(str.indexOf(">") + 1, str.lastIndexOf("<")).trim();
//            Log.e("temp", temp);
            if (i % 2 == 0) {
                courseWare = new CourseWare(temp);
                list.add(courseWare);
            } else {
                courseWare.setTime(temp);
            }
            i++;
        }

//获取课件名称
        Pattern p2 = Pattern.compile("hspace=5>(.*)</a>");
        Matcher m2 = p2.matcher(s);
        int j = 0;
        while (m2.find()) {
            String str = m2.group(0);
            //            hspace=5>第一讲绪论与信号.ppt</a>
            String temp = str.substring(str.indexOf(">") + 1, str.indexOf("<")).trim();
            list.get(j).setName(temp);
            j++;
        }

        //获取课件下载地址
        Pattern p3 = Pattern.compile("href='(.*)' target");
        Matcher m3 = p3.matcher(s);
        int k = 0;
        while (m3.find()) {
            String str = m3.group(0);
            String temp = str.substring(str.indexOf("'")+1,str.lastIndexOf("'"));
            String url = "http://e.ncut.edu.cn/eclass/eclass/document/";
            String address =  url+temp;
            list.get(k).setAddress(address);
//            goto/?doc_url=%252F20160412114304.ppt
//            http://e.ncut.edu.cn/eclass/eclass/document/goto/?doc_url=%252F20160301131957.ppt
            k++;
        }
//        Log.e("list", list.toString());
        return list;

    }
}
