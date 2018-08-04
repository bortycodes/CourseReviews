package com.teamgp.courses.dao;

import com.teamgp.courses.exc.DaoException;
import com.teamgp.courses.model.Review;

import java.util.List;

public interface ReviewDao {
  void add(Review review) throws DaoException;

  List<Review> findAll();
  List<Review> findByCourseId(int courseId);
}
