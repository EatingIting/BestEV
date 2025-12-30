package com.ysc.bestev.Data;

import androidx.lifecycle.ViewModel;

public class UserInformationViewModel extends ViewModel {

    private UserInformation userInformation;

    public UserInformation getUserInformation(){
        return userInformation;
    }

    public UserInformationViewModel(){

    }
    public void insert(UserInformation userInformation){
        this.userInformation = userInformation;
    }
}