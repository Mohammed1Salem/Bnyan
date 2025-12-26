package com.example.bnyan.Repository;

import com.example.bnyan.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review getReviewById(Integer id);

}
