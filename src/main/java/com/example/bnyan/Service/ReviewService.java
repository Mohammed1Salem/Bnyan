package com.example.bnyan.Service;

import com.example.bnyan.Api.ApiException;
import com.example.bnyan.Model.Customer;
import com.example.bnyan.Model.Review;
import com.example.bnyan.Repository.CustomerRepository;
import com.example.bnyan.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    public List<Review> get() {
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found");
        }
        return reviews;
    }

    public void add(Integer customerId, Review review) {
        Customer customer = customerRepository.getCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        review.setCustomer(customer);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public void delete(Integer reviewId, Integer customerId) {
        Customer customer = customerRepository.getCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        Review review = reviewRepository.getReviewById(reviewId);
        if (review == null) {
            throw new ApiException("Review not found");
        }
        if (!review.getCustomer().getId().equals(customerId)) {
            throw new ApiException("You are not authorized to delete this review");
        }
        reviewRepository.delete(review);
    }

    ///  extra endpoints

    public Review getReviewById(Integer id) {
        Review review = reviewRepository.getReviewById(id);
        if (review == null) {
            throw new ApiException("Review not found");
        }
        return review;
    }


}
