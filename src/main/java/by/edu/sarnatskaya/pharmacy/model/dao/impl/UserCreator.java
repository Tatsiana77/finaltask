package by.edu.sarnatskaya.pharmacy.model.dao.impl;

import by.edu.sarnatskaya.pharmacy.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;

public class UserCreator {

    public UserCreator() {
    }

    static User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setName(resultSet.getString(NAME));
        user.setSurname(resultSet.getString(SURNAME));
        user.setLogin(resultSet.getString(LOGIN));
        user.setEmail(resultSet.getString(EMAIL));
        user.setPhone(resultSet.getString(PHONE));
        user.setRole(User.Role.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
        user.setStatus(User.Status.valueOf(resultSet.getString(USER_STATUS).toUpperCase()));
        return user;
    }
}
