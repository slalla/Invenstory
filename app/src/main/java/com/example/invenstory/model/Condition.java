package com.example.invenstory.model;

import androidx.annotation.NonNull;

/**
 * This enum represents the condition of an item. Each condition represents a range from 0-10 inclusive
 * These ranges are as follows POOR[0,2), FAIR[2,4) etc with MINT representing [10]
 */
public enum Condition {
    POOR,
    FAIR,
    GOOD,
    FINE,
    NEARMINT,
    MINT,
    NA
    ;

    public Condition getCondition() {
        Condition result;
        switch(this.ordinal()) {
            case 0:
                result = Condition.POOR;
                break;
            case 1:
                result = Condition.FAIR;
                break;
            case 2:
                result = Condition.GOOD;
                break;
            case 3:
                result = Condition.FINE;
                break;
            case 4:
                result = Condition.NEARMINT;
                break;
            case 5:
                result = Condition.MINT;
                break;
            default:
                result = Condition.NA;
        }
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        String result;
        switch(this.ordinal()) {
            case 0:
                result = "Poor";
                break;
            case 1:
                result = "Fair";
                break;
            case 2:
                result = "Good";
                break;
            case 3:
                result = "Fine";
                break;
            case 4:
                result = "Near Mint";
                break;
            case 5:
                result = "Mint";
                break;
            default:
                result = "NA";
        }
        return result;
    }
}