package com.teamgp.courses.dao;

import static org.junit.Assert.*;

import com.teamgp.courses.exc.DaoException;
import com.teamgp.courses.model.Course;
import com.teamgp.courses.model.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oReviewDaoTest {

  private Connection conn;
  private Sql2oCourseDao courseDao;
  private Sql2oReviewDao reviewDao;
  private Course course;

  @Before
  public void setUp() throws Exception {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    //keep connection open throughout test so it isn't wiped out
    conn = sql2o.open();

    courseDao = new Sql2oCourseDao(sql2o);
    reviewDao = new Sql2oReviewDao(sql2o);

    course = new Course("Test", "http://test.com");
    courseDao.add(course);
  }

  @After
  public void tearDown() throws Exception {
    conn.close();
  }

  @Test
  public void addReviewSetsNewId() throws Exception {
    Review review = new Review(course.getId(), 5, "Great Test course!");
    int originalId = review.getId();

    reviewDao.add(review);

    assertNotEquals(originalId, review.getId());
  }

  @Test
  public void multipleReviewsAreFoundWhenTheyExistForACourse() throws Exception {
    reviewDao.add(new Review(course.getId(), 3, "Test comment 2"));
    reviewDao.add(new Review(course.getId(), 1, "Test comment 3"));
    reviewDao.add(new Review(course.getId(), 2, "Test comment 4"));
    List<Review> reviews = reviewDao.findByCourseId(course.getId());

    assertEquals(3, reviews.size());
  }

  @Test(expected = DaoException.class)
  public void addingAReviewToANonExistingCourseFails() throws Exception {
    Review review = new Review(52, 5, "test comment");
    reviewDao.add(review);
  }
}