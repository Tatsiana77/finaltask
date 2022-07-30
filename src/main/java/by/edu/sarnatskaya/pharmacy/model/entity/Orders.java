package by.edu.sarnatskaya.pharmacy.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Orders extends AbstractEntity {
    private Map<Preparation, Integer> preparations;
    private LocalDateTime created;
    private BigDecimal amount;
    private BigDecimal dose;
    private BigDecimal cost;
    private OrdersStatus status;
    private long userId;


    public enum OrdersStatus {
        REFUSE("отказ"),
        PREPARING("готов"),
        DELIVERED("доставлено"),
        ORDERED("заказан");

        String value;

        OrdersStatus(String status) {
            this.value = status;
        }

        OrdersStatus() {
        }

        public String getStatus() {
            return value;
        }
    }


    public Orders() {
    }

    public Orders(Map<Preparation, Integer> preparations,
                  LocalDateTime created, BigDecimal amount, BigDecimal dose,
                  BigDecimal cost, OrdersStatus status, long userId) {
        this.preparations = preparations;
        this.created = created;
        this.amount = amount;
        this.dose = dose;
        this.cost = cost;
        this.status = status;
        this.userId = userId;
    }

    public Map<Preparation, Integer> getPreparations() {
        return preparations;
    }

    public void setPreparations(Map<Preparation, Integer> preparations) {
        this.preparations = preparations;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDose() {
        return dose;
    }

    public void setDose(BigDecimal dose) {
        this.dose = dose;
    }


    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public OrdersStatus getStatus() {
        return status;
    }

    public void setStatus(OrdersStatus status) {
        this.status = this.status;
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
       Orders orders = (Orders) obj;
        if (created != null ? !created.equals(orders.created) : orders.created != null)
            return false;
        if (amount!= null ? !amount.equals(orders.amount) : orders.amount!= null)
            return false;
        if(dose !=null ? !dose.equals(orders.dose) : orders.dose !=null)
            return false;
        if(cost !=null ? !cost.equals(orders.cost) : orders.cost !=null)
            return false;
        if (userId != orders.userId)
            return false;
        if(status != orders.status)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (created != null ? created.hashCode() : 0);
        result = prime * result + (amount != null ? amount.hashCode() : 0);
        result = prime * result + (dose != null ? dose.hashCode() : 0);
        result = prime * result + (cost != null ? cost.hashCode() : 0);
        result = prime * result + (status != null ? status.hashCode() : 0);
        result = prime * result + (preparations != null ? preparations.hashCode() : 0);
        result = prime * result + Long.hashCode(userId);

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Orders[")
                .append(", preparations").append(preparations)
                .append(", created=").append(created)
                .append(", amount = ").append(amount)
                .append(", dose = ").append(dose)
                .append(", cost = ").append(cost)
                .append(",ordersStatus = ").append(status)
                .append(", userId =").append(userId)
                .append("]").toString();

    }
}
