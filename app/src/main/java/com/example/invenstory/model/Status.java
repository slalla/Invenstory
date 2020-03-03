package com.example.invenstory.model;

import androidx.annotation.NonNull;

public enum Status {
    ACTIVE,
    LOST,
    LOANED,
    NA;

    public Status getStatus(){
        Status result;
        switch(this.ordinal()){
            case 0:
                result = ACTIVE;
                break;
            case 1:
                result = LOST;
                break;
            case 2:
                result = LOANED;
                break;
            default:
                result = NA;
        }
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        String result;
        switch(this.ordinal()){
            case 0:
                result = "Active";
                break;
            case 1:
                result = "Lost";
                break;
            case 2:
                result = "Loaned";
                break;
            default:
                result = "NA";
        }
        return result;
    }
}
