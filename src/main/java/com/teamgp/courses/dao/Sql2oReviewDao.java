package com.teamgp.courses.dao;

import com.teamgp.courses.exc.DaoException;
import com.teamgp.courses.model.Course;
import com.teamgp.courses.model.Review;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oReviewDao implements ReviewDao {
  private final Sql2o sql2o;

  Sql2oReviewDao(Sql2o sql2o){
    this.sql2o = sql2o;
  }

  @Override
  public void add(Review review) throws DaoException {
    String sql = "INSERT INTO reviews (course_id, rating, comment) VALUES (:courseId, :rating, :comment)";
    try(Connection conn = sql2o.open()){
      int id = (int) conn.createQuery(sql)
                      .bind(review)
                      .executeUpdate()
                      .getKey();
      review.setId(id);
    }catch(Sql2oException ex){
      throw new DaoException(ex, "Problem adding review. " + ex.toString());
    }
  }

  @Override
  public List<Review> findAll() {
    try(Connection conn = sql2o.open()) {
      return conn.createQuery("SELECT * FROM reviews")
          .executeAndFetch(Review.class);
    }
  }

  @Override
  public List<Review> findByCourseId(int courseId) {
    try(Connection conn = sql2o.open()){
      return conn.createQuery("SELECT * FROM reviews WHERE course_id = :courseId")
              .addColumnMapping("COURSE_ID", "courseId")
              .addParameter("courseId", courseId)
              .executeAndFetch(Review.class);
    }
  }
}
