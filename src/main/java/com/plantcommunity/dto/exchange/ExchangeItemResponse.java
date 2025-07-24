package com.plantcommunity.dto.exchange;

import com.plantcommunity.entity.ExchangeItem;
import com.plantcommunity.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class ExchangeItemResponse {
    private Long id;
    private SellerInfo seller;
    private String category;
    private String title;
    private String description;
    private int price;
    private List<String> images;
    private String condition;
    private List<String> tags;
    private boolean isFavorite;
    private boolean isAuthor;
    private boolean isAvailable;
    private int viewsCount;
    private int favoritesCount;
    private LocalDateTime createdAt;

    public static class SellerInfo {
        private Long id;
        private String nickname;
        private String profileImage;
        private double rating = 4.8; // TODO: 실제 평점 계산
        private String location;
        private int reviewsCount = 0; // TODO: 실제 리뷰 수 계산

        public SellerInfo(User user, String location) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.profileImage = user.getProfileImage();
            this.location = location;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNickname() { return nickname; }
        public void setNickname(String nickname) { this.nickname = nickname; }

        public String getProfileImage() { return profileImage; }
        public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

        public double getRating() { return rating; }
        public void setRating(double rating) { this.rating = rating; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public int getReviewsCount() { return reviewsCount; }
        public void setReviewsCount(int reviewsCount) { this.reviewsCount = reviewsCount; }
    }

    // Constructors
    public ExchangeItemResponse() {}

    public ExchangeItemResponse(ExchangeItem item, boolean isFavorite, boolean isAuthor) {
        this.id = item.getId();
        this.seller = new SellerInfo(item.getSeller(), item.getLocation());
        this.category = item.getCategory().name().toLowerCase();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.images = item.getImages();
        this.condition = item.getCondition().name().toLowerCase();
        this.tags = item.getTags();
        this.isFavorite = isFavorite;
        this.isAuthor = isAuthor;
        this.isAvailable = item.isAvailable();
        this.viewsCount = item.getViewsCount();
        this.favoritesCount = item.getFavoritesCount();
        this.createdAt = item.getCreatedAt();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SellerInfo getSeller() { return seller; }
    public void setSeller(SellerInfo seller) { this.seller = seller; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public boolean isAuthor() { return isAuthor; }
    public void setAuthor(boolean author) { isAuthor = author; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public int getViewsCount() { return viewsCount; }
    public void setViewsCount(int viewsCount) { this.viewsCount = viewsCount; }

    public int getFavoritesCount() { return favoritesCount; }
    public void setFavoritesCount(int favoritesCount) { this.favoritesCount = favoritesCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
