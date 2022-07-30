package by.edu.sarnatskaya.pharmacy.model.service.impl;

import by.edu.sarnatskaya.pharmacy.exception.ServiceException;
import by.edu.sarnatskaya.pharmacy.model.entity.Prescription;
import by.edu.sarnatskaya.pharmacy.exception.DaoException;
import by.edu.sarnatskaya.pharmacy.model.dao.PrescriptionDao;
import by.edu.sarnatskaya.pharmacy.model.dao.DaoProvider;
import by.edu.sarnatskaya.pharmacy.model.service.PrescriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PrescriptionServiceImpl implements PrescriptionService {
    private static final Logger logger = LogManager.getLogger();
    private static final PrescriptionDao prescriptionDao = DaoProvider.getInstance().getPrescriptionDao();

    @Override
    public List<Prescription> findPrescriptionById(long prescriptionId) throws ServiceException {
        return null;
    }

    @Override
    public List<Prescription> findPrescriptionByUserID(long userId) throws ServiceException {
        return null;
    }

    @Override
    public List<Prescription> findPrescriptionByPreparation(long preparationId) throws ServiceException {
        return null;
    }

    @Override
    public boolean updatePrescriptionByDoctorId(long prescriptionId, long doctorId) throws ServiceException {
        return false;
    }

    @Override
    public long insertNewPrescription(Prescription prescription) throws ServiceException {
        return 0;
    }

    @Override
    public List<Prescription> findAll() throws ServiceException {
        return null;
    }

    @Override
    public List<Prescription> findPrescriptionByStatusRequest(long requestId) throws ServiceException {
        return null;
    }
}
