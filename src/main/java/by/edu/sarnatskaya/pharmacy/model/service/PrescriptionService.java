package by.edu.sarnatskaya.pharmacy.model.service;

import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Prescription;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> findPrescriptionById(long prescriptionId)throws ServiceException;

    List<Prescription> findPrescriptionByUserID(long userId)throws ServiceException;

    List<Prescription> findPrescriptionByPreparation(long preparationId) throws ServiceException;

    boolean updatePrescriptionByDoctorId(long prescriptionId, long doctorId)throws ServiceException;

    long insertNewPrescription (Prescription prescription)throws ServiceException;

    List<Prescription> findAll()throws ServiceException;

    List<Prescription>findPrescriptionByStatusRequest(long requestId)throws ServiceException;
}
