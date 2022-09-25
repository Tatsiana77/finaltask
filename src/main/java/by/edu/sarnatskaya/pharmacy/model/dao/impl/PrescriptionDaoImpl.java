package by.edu.sarnatskaya.pharmacy.model.dao.impl;


import by.edu.sarnatskaya.pharmacy.model.entity.Prescription;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.connection.ConnectionPool;
import by.edu.sarnatskaya.pharmacy.model.dao.PrescriptionDao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static by.edu.sarnatskaya.pharmacy.model.dao.ColumnName.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PrescriptionDaoImpl implements PrescriptionDao {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

    private static final String FIND_PRESCRIPTION_BY_ID = "SELECT prescription.id, doctor_id, date, time, is_active, prescriptions.users_id, prescriptions.preparations_id" +
            "FROM prescriptions " +
            "JOIN preparations ON prescriptions.preparations_id =preparations.id " +
            "JOIN users ON prescriptions.users_id = users.id " +
            "WHERE prescriptions.id =?  ";

    private static final String FIND_ALL_PRESCRIPTION = "SELECT  id, doctor_id, date, time, is_active, comment, preparations_id, users_id FROM prescriptions";

    private static final String FIND_PRESCRIPTION_BY_USER_ID = "SELECT id, doctor_id, date, time, is_active ,  prescriptions.users_id, prescriptions.preparations_id " +
            "FROM prescriptions" +
            " JOIN users ON prescriptions.users_id = users.id " +
            "JOIN preparations ON prescriptions.preparations_id =preparations.id " +
            "WHERE prescriptions.users_id =?  ";

    private static final String FIND_PRESCRIPTION_BY_PREPARATION = "SELECT prescription.id, doctor_id, date, time, is_active, prescriptions.users_id, prescriptions.preparations_id " +
            "FROM prescriptions" +
            "JOIN preparations ON prescriptions.preparations_id =preparations.id " +
            "WHERE prescriptions.preparations_id = ? ";

    private static final String UPDATE_PRESCRIPTION_BY_DOCTOR = "UPDATE prescriptions SET doctor_id =? WHERE id =?";

    private static final String INSERT_NEW_PRESCRIPTION = "INSERT INTO prescriptions (doctor_id, date, time, is_active, comment, preparations_id, users_id) " +
            "VALUES (?, ? , ? , ? , ?, ? , ?)";

    private static final String DELETE_PRESCRIPTION_BY_ID = "DELETE FROM prescriptions WHERE prescriptions.id =?";

    @Override
    public List<Prescription> findAll() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_PRESCRIPTION)) {
            List<Prescription> prescriptions = new ArrayList<>();
            while (resultSet.next()) {
                Prescription prescription = PrescriptionCreator.createPrescription(resultSet);
                prescriptions.add(prescription);
            }
            logger.debug("findAllmethod was completed successfully. " + prescriptions.size() + " were found");
            return prescriptions;
        } catch (DaoException | SQLException e) {
            logger.error("Impossible to find prescriptions. Database access error:", e);
            throw new DaoException("Impossible to find prescriptions. Database access error:", e);
        }
    }


    @Override
    public List<Prescription> findPrescriptionById(long prescriptionId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PRESCRIPTION_BY_ID)) {
            statement.setLong(1, prescriptionId);
            ResultSet resultSet = statement.executeQuery();
            List<Prescription> prescriptions = new ArrayList<>();
            if (resultSet.next()) {
                Prescription prescription = PrescriptionCreator.createPrescription(resultSet);
                prescriptions.add(prescription);
            }
            logger.log(Level.DEBUG, "isPrescriptionById method was completed successfully. Result: " + prescriptions);
            return prescriptions;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find prescription by id from database. Database access error:", e);
            throw new DaoException("Impossible to find prescription by id from database. Database access error:", e);
        }
    }

    @Override
    public List<Prescription> findPrescriptionByUserID(long userId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PRESCRIPTION_BY_USER_ID)) {
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Prescription> prescriptions = new ArrayList<>();
            if (resultSet.next()) {
                Prescription prescription = PrescriptionCreator.createPrescription(resultSet);
                prescription.setId(resultSet.getLong(PRESCRIPTION_ID));
                prescription.setDoctorId(resultSet.getLong(PRESCRIPTION_DOCTOR_ID));
                prescription.setDate(LocalDateTime.parse(resultSet.getString(PRESCRIPTION_DATE)));
                prescription.setTime(LocalDateTime.parse(resultSet.getString(PRESCRIPTION_TIME)));
                prescription.setActive(resultSet.getBoolean(PRESCRIPTION_ACTIVE));
                prescription.setComment(resultSet.getString(PRESCRIPTION_COMMENT));
                prescription.setPreparationId(resultSet.getLong(PRESCRIPTION_PREPARATION_ID));
                prescriptions.add(prescription);
            }
            logger.log(Level.DEBUG, !prescriptions.isEmpty() ? prescriptions.size() + " prescriptions were found with user id " + userId
                    : "There weren't any prescriptions with user id " + userId);
            return prescriptions;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find prescriptions with user id " + userId + " into database. Database access error:", e);
            throw new DaoException("Impossible to find prescriptions with user id " + userId + " into database. Database access error:", e);
        }
    }

    @Override
    public List<Prescription> findPrescriptionByPreparation(long preparationId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PRESCRIPTION_BY_PREPARATION)) {
            statement.setLong(1, preparationId);
            ResultSet resultSet = statement.executeQuery();
            List<Prescription> prescriptions = new ArrayList<>();
            if (resultSet.next()) {
                Prescription prescription = PrescriptionCreator.createPrescription(resultSet);
                prescription.setId(resultSet.getLong(PRESCRIPTION_ID));
                prescription.setDoctorId(resultSet.getLong(PRESCRIPTION_DOCTOR_ID));
                prescription.setDate(LocalDateTime.parse(resultSet.getString(PRESCRIPTION_DATE)));
                prescription.setTime(LocalDateTime.parse(resultSet.getString(PRESCRIPTION_TIME)));
                prescription.setActive(resultSet.getBoolean(PRESCRIPTION_ACTIVE));
                prescription.setComment(resultSet.getString(PRESCRIPTION_COMMENT));
                prescriptions.add(prescription);
            }
            logger.log(Level.DEBUG, !prescriptions.isEmpty() ? prescriptions.size() + " prescriptions were found with preparation id " + preparationId
                    : "There weren't any prescriptions with preparation id" + preparationId);
            return prescriptions;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to find prescriptions with preparation id" + preparationId + " into database. Database access error:", e);
            throw new DaoException("Impossible to find prescriptions with preparation id" + preparationId + " into database. Database access error:", e);
        }
    }

    @Override
    public boolean updatePrescriptionByDoctorId(long prescriptionId, long doctorId) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRESCRIPTION_BY_DOCTOR)) {
            statement.setLong(1, prescriptionId);
            statement.setLong(2, doctorId);
            boolean result = statement.executeUpdate() == 1;
            logger.log(Level.DEBUG, "Result of prescription update with doctorId " + prescriptionId + " is " + result);
            return result;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to update prescriptions with doctor id. Database access error:", e);
            throw new DaoException("Impossible to update prescriptions with doctor id. Database access error:", e);
        }
    }

    @Override
    public long insertNewPrescription(Prescription prescription) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_NEW_PRESCRIPTION, RETURN_GENERATED_KEYS)) {
            statement.setLong(1, prescription.getDoctorId());
            statement.setTimestamp(2, Timestamp.valueOf(prescription.getDate()));
            statement.setTimestamp(3, Timestamp.valueOf(prescription.getTime()));
            statement.setBoolean(4, prescription.isActive());
            statement.setLong(5, prescription.getPreparationId());
            statement.setLong(6, prescription.getUser().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            long prescriptionId = 0;
            if (resultSet.next()) {
                prescriptionId = resultSet.getLong(1);
                logger.log(Level.INFO, "InsertNewPrescription method was completed successfully. Prescription with id " + prescriptionId + " was added");
            }
            return prescriptionId;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible to insert prescription into database. Database access error:", e);
            throw new DaoException("Impossible to insert prescription into database. Database access error:", e);
        }
    }
}
