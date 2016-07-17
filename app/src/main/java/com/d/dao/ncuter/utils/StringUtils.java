package com.d.dao.ncuter.utils;

import android.util.Log;

import com.d.dao.ncuter.bean.Course;
import com.d.dao.ncuter.bean.CourseSummary;
import com.d.dao.ncuter.bean.CourseWare;
import com.d.dao.ncuter.bean.PersonInfo;
import com.d.dao.ncuter.bean.TeachExam;
import com.d.dao.ncuter.bean.TeachGpa;
import com.d.dao.ncuter.bean.TeachScore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public static List<Course> getCourseMsg(String msg) {
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
            String temp = str.substring(str.indexOf("'") + 1, str.lastIndexOf("'"));
            String url = "http://e.ncut.edu.cn/eclass/eclass/document/";
            String address = url + temp;
            list.get(k).setAddress(address);
//            goto/?doc_url=%252F20160412114304.ppt
//            http://e.ncut.edu.cn/eclass/eclass/document/goto/?doc_url=%252F20160301131957.ppt
            k++;
        }
//        Log.e("list", list.toString());
        return list;

    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String inputStream2String(InputStream in, String encoding) throws IOException {
        StringBuffer out = new StringBuffer();
        InputStreamReader inread = new InputStreamReader(in, encoding);
        char[] b = new char[4096];
        for (int n; (n = inread.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }


    // 获取考试成绩信息集合
    public static List<TeachScore> getTeachScoreList(String s) throws Exception {
        String result = "";
        Pattern p = Pattern.compile("align=center(.*)</TD>");
        Matcher m = p.matcher(s);
        while (m.find()) {
            result = m.group(0);
        }
//正则莫名失败
//                            Pattern p2 = Pattern.compile("<TD align=center>(.*)</TD>");
//                            Matcher m2 = p2.matcher(result);
//                            int index =0;
//                            while (m2.find()) {
//                                String temp = m2.group(0);
//                                Log.e("temp",temp);
//                                index++;
//                            }


        String[] temp = result.split("</TD>");
        int len = temp.length;
        for (int i = 0; i < len; i++) {
            String str = temp[i];
            String temp2 = str.substring(str.lastIndexOf(">") + 1);
            String temp3 = "";
            if (temp2.contains("&")) {
                temp3 = temp2.substring(0, temp2.indexOf("&"));
            } else {
                temp3 = temp2;
            }
            temp[i] = temp3;
        }
        int counter = len / 11;
        List<TeachScore> scoreList = new ArrayList<>();
        String str = "";
        for (int i = 0; i < counter; i++) {
            TeachScore teachScore = new TeachScore();
            for (int j = 0; j < 11; j++) {
                str = temp[i * 11 + j];
                switch (j) {
                    case 0:
                        teachScore.setYear(str);
                        break;
                    case 1:
                        teachScore.setSeason(str);
                        break;
                    case 2:
                        teachScore.setClass_code(str);
                        break;
                    case 3:
                        teachScore.setClass_name(str);
                        break;
                    case 4:
                        teachScore.setScore_normal(str);
                        break;
                    case 5:
                        teachScore.setScore_final(str);
                        break;
                    case 6:
                        teachScore.setScore_overall(str);
                        break;
                    case 7:
                        teachScore.setPercent_normal(str);
                        break;
                    case 8:
                        teachScore.setExam_nature(str);
                        break;
                    case 9:
                        teachScore.setCredit(str);
                        break;
                    case 10:
                        teachScore.setTeacher_name(str);
                        break;
                    default:
                        break;
                }
            }
            scoreList.add(teachScore);
        }
        return scoreList;
    }

    public static List<TeachGpa> getTeachGpaList(String s) throws Exception {
        List<TeachGpa> list = new ArrayList<>();
        s = s.substring(s.indexOf("bgcolor=#ffffcc"));
        s = s.substring(0, s.indexOf("</table>"));
        String[] tmp = s.split("bgcolor");
        int len = tmp.length;

        for (int i = 0; i < len; i++) {
            Log.e("tmp" + i, "hello" + tmp[i]);
        }

        for (int j = 1; j < len; j++) {
            String temp[] = tmp[j].split("</td>");
            int counter = temp.length;
            for (int i = 0; i < counter; i++) {
                if (temp[i].contains("strong")) {
                    temp[i] = temp[i].substring(temp[i].indexOf("<strong>") + 7, temp[i].indexOf("</strong>"));
                }
                if (temp[i].contains("&")) {
                    temp[i] = temp[i].substring(temp[i].lastIndexOf(">") + 1, temp[i].indexOf("&"));
                } else {
                    temp[i] = temp[i].substring(temp[i].lastIndexOf(">") + 1);
                }
            }
            TeachGpa teachGpa = new TeachGpa();
            for (int i = 0; i < counter; i++) {
                switch (i) {
                    case 0:
                        teachGpa.setCourse_type(temp[i]);
                        break;
                    case 1:
                        teachGpa.setCourse_code(temp[i]);
                        break;
                    case 2:
                        teachGpa.setCourse_name(temp[i]);
                        break;
                    case 3:
                        teachGpa.setCourse_credit(temp[i]);
                        break;
                    case 4:
                        teachGpa.setScore(temp[i]);
                        break;
                    case 5:
                        teachGpa.setGpa(temp[i]);
                        break;
                    case 6:
                        teachGpa.setRemark(temp[i]);
                        break;
                    case 7:
                        break;
                    default:
                        break;
                }
            }
            list.add(teachGpa);
        }
        return list;
    }

    public static List<TeachExam> getTeachExamSchedule(String s) throws Exception {
        String result = "";
        Pattern p = Pattern.compile("<TR height=30>(.*)</TR>");
        Matcher m = p.matcher(s);
        while (m.find()) {
            result = m.group(0);
            Log.e("result", result);
        }

        Pattern p2 = Pattern.compile("center>([^</]+)</");
        Matcher m2 = p2.matcher(result);
        List<String> stringList = new ArrayList();
        while (m2.find()) {
            String str = m2.group(1);
            if (str.contains("&")) {
                str = str.substring(0, str.indexOf("&"));
            }
            Log.e("str", str);
            stringList.add(str);
        }
        List<TeachExam> examList = new ArrayList<>();
        int length = stringList.size();
        String tmp = "";
        for (int i = 0; i < length / 6; i++) {
            TeachExam teachExam = new TeachExam();
            for (int j = 0; j < 6; j++) {
                tmp = stringList.get(i * 6 + j);
                switch (j) {
                    case 0:
                        teachExam.setCourse_code(tmp);
                        break;
                    case 1:
                        teachExam.setCourse_name(tmp);
                        break;
                    case 2:
                        teachExam.setExam_site(tmp);
                        break;
                    case 3:
                        teachExam.setExam_date(tmp);
                        break;
                    case 4:
                        teachExam.setExam_time(tmp);
                        break;
                    case 5:
                        teachExam.setMark(tmp);
                        break;
                }
            }
            examList.add(teachExam);
        }
        for (int i = 0; i < length / 6; i++) {
            Log.e("lsit", examList.get(i).toString());
        }
        return examList;
    }


    //截取个人信息
    public static PersonInfo getPersonInfo(String s) {
        PersonInfo personInfo = new PersonInfo();
        List<String> tmpList = new ArrayList<String>();
        String result = "";
        Log.e("未处理的个人信息", "hello");
        Log.e("未处理的个人信息", s);
        Log.e("s", "未处理的个人信息" + s.toString());
        Pattern p = Pattern.compile("&nbsp;(.*)</td>");
        Matcher m = p.matcher(s);
        while (m.find()) {
            String str = m.group(0);
            result = str.substring(str.indexOf(";") + 1, str.indexOf("<"));
            tmpList.add(result);
        }
        int len = tmpList.size();
        Log.e("len",""+len);
        if (len == 9) {
            personInfo.setStudent_id(tmpList.get(0));
            personInfo.setName(tmpList.get(1));
            personInfo.setId_number("");
            personInfo.setEntry_class(tmpList.get(2));
            personInfo.setSex(tmpList.get(3));
            personInfo.setEntry_year(tmpList.get(4));
            personInfo.setSeason(tmpList.get(5));
            personInfo.setDefault_class(tmpList.get(6));
            personInfo.setMajor(tmpList.get(7));
            personInfo.setMajor_field(tmpList.get(8));
        } else {//len=10
            personInfo.setStudent_id(tmpList.get(0));
            personInfo.setName(tmpList.get(1));
            personInfo.setId_number(tmpList.get(2));
            personInfo.setEntry_class(tmpList.get(3));
            personInfo.setSex(tmpList.get(4));
            personInfo.setEntry_year(tmpList.get(5));
            personInfo.setSeason(tmpList.get(6));
            personInfo.setDefault_class(tmpList.get(7));
            personInfo.setMajor(tmpList.get(8));
            personInfo.setMajor_field(tmpList.get(9));
        }
        return personInfo;
    }
}
