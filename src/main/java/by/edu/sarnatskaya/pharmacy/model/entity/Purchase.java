package by.edu.sarnatskaya.pharmacy.model.entity;

import java.util.Objects;

public class Purchase extends AbstractEntity {
    private Integer quantity;
    private Long ordersId;
    private Long preparationsId;
    private Long userId;

    public Purchase(Integer quantity, Long ordersId, Long preparationsId, Long userId) {
        this.quantity = quantity;
        this.ordersId = ordersId;
        this.preparationsId = preparationsId;
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Long getPreparationsId() {
        return preparationsId;
    }

    public void setPreparationsId(Long preparationsId) {
        this.preparationsId = preparationsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        Purchase purchase = (Purchase) obj;
        if (quantity != null ? !quantity.equals(purchase.quantity) : purchase.quantity != null)
            return false;
        if (ordersId != purchase.ordersId)
            return false;
        if (preparationsId != purchase.preparationsId)
            return false;
        if (userId != purchase.userId)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (quantity != null ? quantity.hashCode() : 0);
        result = prime * result + Long.hashCode(ordersId);
        result = prime * result + Long.hashCode(preparationsId);
        result = prime * result + Long.hashCode(userId);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Purchase [")
                .append("quantity=").append(quantity)
                .append(", ordersId=").append(ordersId)
                .append(", preparationsId=").append(preparationsId)
                .append(", userId=").append(userId)
                .append(']').toString();
    }
}
