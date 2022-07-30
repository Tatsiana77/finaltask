package by.edu.sarnatskaya.pharmacy.model.dao.impl;

import by.edu.sarnatskaya.pharmacy.model.entity.Category;
import by.edu.sarnatskaya.pharmacy.model.entity.Preparation;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.util.ImageEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;


import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;

public class PreparationCreator {

    public PreparationCreator() {
    }

    static Preparation createPreparation(ResultSet resultSet) throws SQLException, DaoException {
        Preparation preparation = new Preparation();
        preparation.setId(resultSet.getLong(PREPARATIONS_ID));
        preparation.setTitle(resultSet.getString(PREPARATIONS_TITLE));
        preparation.setPrice(resultSet.getBigDecimal(PREPARATIONS_PRICE));
        preparation.setAmount(resultSet.getBigDecimal(PREPARATIONS_AMOUNT));
        preparation.setDescription(resultSet.getString(PREPARATIONS_DESCRIPTION));
        preparation.setPicture(ImageEncoder.encodeBlob(resultSet.getBlob(PREPARATIONS_IMAGE)));
        preparation.setActive(resultSet.getBoolean(PREPARATIONS_ACTIVE));
        preparation.setCategory(new Category(resultSet.getString(CATEGORIES_TYPE)));
        preparation.setCondition(Preparation.Condition.valueOf(resultSet.getString(CONDITIONS_STATUS ).toUpperCase()));
        return preparation;
    }
}
