package com.plantcommunity.dto.exchange;

import com.plantcommunity.entity.ExchangeItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ExchangeItemRequest {
    @NotNull(message = "카테고리는 필수입니다")
    private ExchangeItem.ExchangeCategory category;

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "설명은 필수입니다")
    private String description;

    @Min(value = 0, message = "가격은 0 이상이어야 합니다")
    private int price;

    @NotNull(message = "상품 상태는 필수입니다")
    private ExchangeItem.ItemCondition condition;

    private List<String> tags;

    @NotBlank(message = "지역은 필수입니다")
    private String location;

    // Constructors
    public ExchangeItemRequest() {}

    // Getters and Setters
    public ExchangeItem.ExchangeCategory getCategory() { return category; }
    public void setCategory(ExchangeItem.ExchangeCategory category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public ExchangeItem.ItemCondition getCondition() { return condition; }
    public void setCondition(ExchangeItem.ItemCondition condition) { this.condition = condition; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
