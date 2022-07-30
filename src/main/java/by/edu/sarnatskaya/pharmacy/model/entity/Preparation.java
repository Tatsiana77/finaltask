package by.edu.sarnatskaya.pharmacy.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Preparation extends AbstractEntity {
    private String title;
    private BigDecimal price;
    private BigDecimal amount;
    private String description;
    private String picture;
    private boolean active;
    private Condition condition;
    private  Category category;

    public enum Condition {
        FREE("без рецепта"), PRESCRIPTION("по рецепту");

        private String value;

        Condition(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public Preparation() {
    }

    public Preparation(String title, BigDecimal price, BigDecimal amount,
                       String description, String picture, boolean active,
                       Condition condition, Category category) {
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.picture = picture;
        this.active = active;
        this.condition = condition;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        Preparation preparation =(Preparation) obj;
        if (title!= null ? ! title.equals(preparation.title) : preparation.title!= null)
            return false;
        if (price!= null ? ! price.equals(preparation.price) : preparation.price!= null)
            return false;
        if (amount!= null ? ! amount.equals(preparation.amount) : preparation.amount!= null)
            return false;
        if (description!= null ? ! description.equals(preparation.description) : preparation.description!= null)
            return false;
        if(condition != preparation.condition)
            return false;
        if(category !=preparation.category)
            return false;
       if(active !=preparation.active)
           return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (title != null ? title.hashCode() : 0);
        result = prime * result + (price != null ? price.hashCode() : 0);
        result = prime * result + (amount != null ? amount.hashCode() : 0);
        result = prime * result + (description != null ? description.hashCode() : 0);
        result = prime * result + (picture != null ? picture.hashCode() : 0);
        result = prime * result + (active ? 1 : 0);
        result = prime *result +(condition !=null ? condition.hashCode() : 0);
        result = prime *result +(category !=null ? category.hashCode() : 0);
        return result;

    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Preparation [")
                .append(", title= ").append(title)
                .append(", price=").append(price)
                .append(", amount=").append(amount)
                .append(", description=").append(description)
                .append(", picture=").append(picture)
                .append(", active=").append(active)
                .append(", condition =  ").append(condition)
                .append(", category =  ").append(category)
                .append("]")
                .toString();
    }
}
