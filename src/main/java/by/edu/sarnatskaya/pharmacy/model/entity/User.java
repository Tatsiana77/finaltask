package by.edu.sarnatskaya.pharmacy.model.entity;


public class User extends AbstractEntity {
    private String name;
    private String surname;
    private String login;
    private String email;
    private String phone;
    private Status status;
    private Role role;

    public enum Status {
        ACTIVE, BLOCKED
    }

    public enum Role {
        ADMIN, CLIENT, GUEST, PHARMACIST
    }

    public User(Role role) {
        this.role = role;
    }

    public User() {
    }

    public User(String name, String surname, String login,
                String email, String phone, User.Role role) {

        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = Status.ACTIVE;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return surname;
    }


    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Status getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.surname = login;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
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
        User user = (User) obj;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null)
            return false;
        if (login != null ? !login.equals(user.login) : user.login != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (phone != null ? phone.equals(user.phone) : user.phone != null)
            return false;
        if (status != user.status)
            return false;
        if (role != user.role)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = prime * result + (surname != null ? surname.hashCode() : 0);
        result = prime * result + (login != null ? login.hashCode() : 0);
        result = prime * result + (email != null ? email.hashCode() : 0);
        result = prime * result + (phone != null ? phone.hashCode() : 0);
        result = prime * result + (status != null ? status.hashCode() : 0);
        result = prime * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append(" , name = ").append(name);
        builder.append(" , surname = ").append(surname);
        builder.append(" , login = ").append(login);
        builder.append(" , email = ").append(email);
        builder.append(" , phone = ").append(phone);
        builder.append(" , status = ").append(status);
        builder.append(" , role = ").append(role);
        return builder.toString();
    }
}
