package com.example.payments.view;

import com.example.payments.entity.RequestStatus;

public interface RequestView {
    Long getId();
    CardView getCard();
    RequestStatus getStatus();
    UserView getUser();
    UserView getAdmin();
}
