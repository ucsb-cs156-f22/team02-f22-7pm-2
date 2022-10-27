package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.UCSBDate;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBDateRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDateTime;

@Api(description = "MenuItemReview")
@RequestMapping("/api/menuitemreview")
@RestController
@Slf4j
public class MenuItemReviewController extends ApiController {
    @Autowired
    MenuItemReviewRepository menuItemReviewRepository;

    @ApiOperation(value = "List all menu item reviews")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<MenuItemReview> allReviews() {
        Iterable<MenuItemReview> reviews = MenuItemReviewRepository.findAll();
        return reviews;
    }

    @ApiOperation(value = "Get a single review")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public MenuItemReview getById(
            @ApiParam("itemid") @RequestParam Long itemid) {
        MenuItemReview menuItemReview = MenuItemReviewRepository.findById(itemid)
                .orElseThrow(() -> new EntityNotFoundException(UCSBDate.class, itemid));

        return menuItemReview;
    }

    @ApiOperation(value = "Create a new menu item review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public MenuItemReview postMenuItemReview(
        @ApiParam("itemid") @RequestParam long itemid,
        @ApiParam("reviewerEmail") @RequestParam String reviewerEmail,
        @ApiParam("stars") @RequestParam int stars,
        @ApiParam("dateReviewed") @RequestParam LocalDateTime dateReviewed,
        @ApiParam("comments") @RequestParam String comments
        )
        {

        MenuItemReview reviews = new MenuItemReview();
        reviews.setItemId.(itemid);
        reviews.setReviewerEmail(reviewerEmail);
        reviews.setStars(stars);
        reviews.setDateReviewed(dateReviewed);
        reviews.setComments(comments);

        MenuItemReview savedMenuItemReviews= MenuItemReviewRepository.save(reviews);

        return savedMenuItemReviews;
    }
    @ApiOperation(value = "Delete a Menu Item Review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public Object deleteMenuItemReviews(
            @ApiParam("itemid") @RequestParam String itemid) {
        MenuItemReview reviews = MenuItemReviewRepository.findById(itemid)
                .orElseThrow(() -> new EntityNotFoundException(MenuItemReview.class, itemid));

        MenuItemReviewRepository.delete(commons);
        return genericMessage("MenuItemReview with id %s deleted".formatted(code));
    }

    @ApiOperation(value = "Update a single menu item review")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public MenuItemReview updateReview(
            @ApiParam("itemid") @RequestParam String itemid,
            @RequestBody @Valid MenuItemReview incoming) {

        MenuItemReview reviews = MenuItemReviewRepository.findById(itemid)
                .orElseThrow(() -> new EntityNotFoundException(MenuItemReview.class, itemid));


        reviews.setItemId.(.incoming.getItemId());
        reviews.setReviewerEmail(.incoming.getReviewerEmail());
        reviews.setStars(.incoming.getStars());
        reviews.setDateReviewed(.incoming.getDateReviewed());
        reviews.setComments(.incoming.getComments());

        MenuItemReviewRepository.save(reviews);

        return reviews;
    }

}