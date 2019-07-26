package com.ejsfbu.app_main.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Transaction")
public class Transaction extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_BANK_ACCOUNT = "bankAccount";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_GOAL = "goal";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_APPROVED = "isApproved";
    public static final String KEY_TYPE = "isWithdraw";
    public static final String KEY_COMPLETED_DATE = "completedDate";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public Date getTransactionCompleteDate() {
        Date date = null;
        try {
            date = fetchIfNeeded().getDate(KEY_COMPLETED_DATE);
        } catch (ParseException e) {
            Log.d("transaction", e.toString());
            e.printStackTrace();
        }
        if (date == null) {
            date = getCreatedAt();
        }
        return date;
    }

    public void setTransactionCompleteDate(Date date) {
        put(KEY_COMPLETED_DATE, date);
    }

    public void setBank(BankAccount bank) {
        put(KEY_BANK_ACCOUNT, bank);
    }

    public BankAccount getBank() {
        return (BankAccount) getParseObject(KEY_BANK_ACCOUNT);
    }

    public Double getAmount() { return getDouble(KEY_AMOUNT); }

    public void setAmount(Double amount) { put(KEY_AMOUNT, amount);}

    public Goal getGoal() { return (Goal) get(KEY_GOAL); }

    public void setGoal(Goal goal) { put(KEY_GOAL, goal);}

    public boolean getApproval() { return getBoolean(KEY_APPROVED); }

    public void setApproval(boolean bool) { put(KEY_APPROVED, bool); }

    // Get whether its a deposit or withdraw
    public boolean getType() { return getBoolean(KEY_TYPE); }

    public void setType(boolean bool) { put(KEY_TYPE, bool); }

    public static class Query extends ParseQuery<Transaction> {
        public Query() {
            super(Transaction.class);
        }

        public Query filterUser(ParseUser user) {
            whereEqualTo(KEY_USER, user);
            return this;
        }

        public Query filterGoal(Goal goal) {
            whereEqualTo(KEY_GOAL, goal);
            return this;
        }

        public Query filterBank(BankAccount bank) {
            whereEqualTo(KEY_BANK_ACCOUNT, bank);
            return this;
        }

        public Query withClasses() {
            include("user");
            include("bankAccount");
            include("goal");
            return this;
        }

        public Query getTopCompleted() {
            setLimit(15);
            orderByDescending(KEY_CREATED_AT);
            return this;
        }
    }

}
