package com.revature.service;

import com.revature.model.Application;
import com.revature.repository.ApplicationDAOImpl;

import java.util.ArrayList;



public class ApplicationService {

    ApplicationDAOImpl applicationDAO = new ApplicationDAOImpl();

    public ArrayList<Application> getApplications() {
        return applicationDAO.getApplications();
    }

    public Application getApplicationById(int applicationId) {
        return applicationDAO.getApplicationById(applicationId);
    }

    public boolean addApplication(Application application) {
        return applicationDAO.addApplication(application);
    }

    public boolean deleteApplication(int applicationId) {
        return applicationDAO.deleteApplication(applicationId);
    }
}
