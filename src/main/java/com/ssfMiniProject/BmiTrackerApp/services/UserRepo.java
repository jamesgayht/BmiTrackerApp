package com.ssfMiniProject.BmiTrackerApp.services;

import com.ssfMiniProject.BmiTrackerApp.models.UserBmi;

public interface UserRepo {
    
    public void save(final UserBmi userBmi); 
    public UserBmi findById (final String userId);
    
}
