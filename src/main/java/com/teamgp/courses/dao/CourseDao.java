package com.teamgp.courses.dao;

import com.teamgp.courses.exc.DaoException;
import com.teamgp.courses.model.Course;

import java.util.List;

public interface CourseDao {
  void add(Course course) throws DaoException;

  List<Course> findAll();
}
