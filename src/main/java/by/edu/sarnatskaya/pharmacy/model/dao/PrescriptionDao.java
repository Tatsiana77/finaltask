package by.edu.sarnatskaya.pharmacy.model.dao;

import by.edu.sarnatskaya.pharmacy.model.entity.Prescription;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;

import java.util.List;

public interface PrescriptionDao extends BaseDao<Prescription>{

     List<Prescription>  findPrescriptionById(long prescriptionId)throws DaoException;

     List<Prescription> findPrescriptionByUserID(long userId)throws DaoException;

     List<Prescription> findPrescriptionByPreparation(long preparationId) throws DaoException;

     boolean updatePrescriptionByDoctorId(long prescriptionId, long doctorId)throws DaoException;

     long insertNewPrescription (Prescription prescription)throws DaoException;








}
