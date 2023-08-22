package com.example.payments.dto.view;

import com.example.payments.entity.Role;
import com.example.payments.entity.Status;

public interface UserView {
    Long getId();
    String getName();
    String getSurname();
    String getMiddleName();
    String getPassword();
    String getPhoneNumber();
    Role getRole();
    Status getStatus();
}
