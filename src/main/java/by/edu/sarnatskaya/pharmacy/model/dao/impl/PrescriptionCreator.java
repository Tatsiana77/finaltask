package by.edu.sarnatskaya.pharmacy.model.dao.impl;


import by.edu.sarnatskaya.pharmacy.model.entity.Prescription;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;


import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;

public class PrescriptionCreator {
    public PrescriptionCreator() {
    }
    static Prescription createPrescription(ResultSet resultSet) throws SQLException, DaoException{
        Prescription prescription = new Prescription();
        prescription.setId(resultSet.getLong(PRESCRIPTION_ID));
        prescription.setDoctorId(resultSet.getLong(PRESCRIPTION_DOCTOR_ID));
        prescription.setDate(LocalDateTime.parse(resultSet.getString(PRESCRIPTION_DATE)));
        prescription.setTime(LocalDateTime.parse(resultSet.getString(PRESCRIPTION_TIME)));
        prescription.setActive(resultSet.getBoolean(PRESCRIPTION_ACTIVE));
        prescription.setComment(resultSet.getString(PRESCRIPTION_COMMENT));
        prescription.setPreparationId(resultSet.getLong(PRESCRIPTION_PREPARATION_ID));
        return prescription;
    }
}
