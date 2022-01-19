package com.revature.repository;

import com.revature.model.Application;

import java.util.ArrayList;

public interface ApplicationDAO {

    ArrayList<Application> getApplications();

    Application getApplicationById(int id);

    boolean addApplication(Application application);

    boolean deleteApplication(int applicationId);

    ArrayList<Application> getApplicationByAccountId(int accountId);
}
