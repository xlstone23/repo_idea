package com.lagou.service.impl;

import com.lagou.dao.CourseMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Teacher;
import com.lagou.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    /*
    多条件课程列表查询
     */
    @Override
    public List<Course> findCourseByCondition(CourseVo courseVo) {
        List<Course> list = courseMapper.findCourseByCondition(courseVo);
        return list;
    }

    /*
       添加课程及讲师信息
        */
    @Override
    public void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //封装课程信息
        Course course=new Course();

        BeanUtils.copyProperties(course,courseVo);

        //补全课程信息
        Date date=new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);

        //保存课程
        courseMapper.saveCourse(course);

        //获取新插入数据的id
        int id = course.getId();

        //封装讲师信息
        Teacher teacher=new Teacher();
        BeanUtils.copyProperties(teacher,courseVo);

        //补全教师信息
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        teacher.setCourseId(id);

        //保存讲师信息
        courseMapper.saveTeacher(teacher);
    }

    @Override
    public CourseVo findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //封装课程信息
        Course course=new Course();
        BeanUtils.copyProperties(course,courseVo);

        //补全信息
        Date date=new Date();
        course.setUpdateTime(date);

        //更新课程信息
        courseMapper.updateCourse(course);

        Teacher teacher=new Teacher();
        BeanUtils.copyProperties(teacher,courseVo);

        //补全信息
        int id = course.getId();
        teacher.setCourseId(id);
        teacher.setUpdateTime(date);

        courseMapper.updateTeacher(teacher);
    }

    @Override
    public void updateCourseStatus(int courseid,int status) {
        Course course=new Course();
        course.setId(courseid);
        course.setStatus(status);
        course.setUpdateTime(new Date());

        courseMapper.updateCourseStatus(course);
    }
}
