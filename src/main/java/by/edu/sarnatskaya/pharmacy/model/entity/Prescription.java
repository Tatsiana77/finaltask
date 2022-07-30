package by.edu.sarnatskaya.pharmacy.model.entity;


import java.time.LocalDateTime;
import java.util.Objects;

public class Prescription extends AbstractEntity {
    private long doctorId;
    private LocalDateTime date;
    private LocalDateTime time;
    private boolean isActive;
    private String comment;
    private long preparationId;
    private User user;

    public Prescription() {
    }

    public Prescription(long doctorId, LocalDateTime date, LocalDateTime time,
                        boolean isActive, String comment, long preparationId, User user) {
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.isActive = isActive;
        this.comment = comment;
        this.preparationId = preparationId;
        this.user = user;

    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getPreparationId() {
        return preparationId;
    }

    public void setPreparationId(long preparationId) {
        this.preparationId = preparationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Prescription prescription = (Prescription) obj;
        if (doctorId != prescription.doctorId)
            return false;
        if (date != null ? !date.equals(prescription.date) : prescription.date != null)
            return false;
        if (time != null ? !time.equals(prescription.time) : prescription.time != null)
            return false;
        if (isActive != prescription.isActive)
            return false;
        if (comment != null ? !comment.equals(prescription.comment) : prescription.comment != null)
            return false;
        if (preparationId != prescription.preparationId)
            return false;
        if (user != user)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Long.hashCode(doctorId);
        result = prime * result + (date != null ? date.hashCode() : 0);
        result = prime * result + (time != null ? time.hashCode() : 0);
        result = prime * result + (isActive ? 1 : 0);
        result = prime * result + (comment != null ? comment.hashCode() : 0);
        result = prime * result + Long.hashCode(preparationId);
        result = prime * result + (user != null ? user.hashCode() : 0);


        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Prescription[ ")
                .append("doctorId=").append(doctorId)
                .append(", date=").append(date)
                .append(", time=").append(time)
                .append(", isActive=").append(isActive)
                .append(", comment=").append(comment)
                .append(", preparationId=").append(preparationId)
                .append(", user=").append(user)
                .append(" ]").toString();
    }
}
