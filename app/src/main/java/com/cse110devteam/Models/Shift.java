package com.cse110devteam.Models;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by anthonyaltieri on 3/11/16.
 */
public class Shift {
    public static final int VACANT = 0;
    public static final int FILLED = 1;

    private int status;
    private ParseObject business;
    private ParseUser employee;
    private Date start;
    private Date end;
    private String firstname;
    private String lastname;
    private String id;

    public Shift() {};

    public int getStatus()
    {
        return status;
    }

    public String getFirstname()
    {
        return firstname;

    }

    public String getLastname()
    {
       return lastname;
    }

    public ParseObject getBusiness()
    {
        return business;
    }

    public ParseObject getEmployee()
    {
        return employee;
    }

    public Date getStart()
    {
        return start;
    }

    public Date getEnd()
    {
        return end;
    }

    public String getId()
    {
        return id;
    }

    public String getFirstName;

    public static class Builder {
        private final int mStatus;
        private ParseObject mBusiness;
        private ParseUser mEmployee;
        private Date mStart;
        private Date mEnd;
        private String fName;
        private String lName;
        private String mOid;

        public Builder( int status )
        {
            mStatus = status;
        }

        public Builder employee( ParseUser employee )
        {
            if ( employee == null )
            {
                mEmployee = null;
            }
            else
            {
                mEmployee = employee;
                try {
                    fName = (String) employee.fetchIfNeeded().get("firstname");
                    lName = (String) employee.fetchIfNeeded().get("lastname");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return this;
        }

        public Builder business( ParseObject business )
        {
            mBusiness = business;
            return this;
        }

        public Builder id( String oid )
        {
            mOid = oid;
            return this;
        }

        public Builder start( Date start )
        {
            mStart = start;
            return this;
        }

        public Builder end( Date end )
        {
            mEnd = end;
            return this;
        }

        public Shift build()
        {
            Shift shift = new Shift();
            shift.status = mStatus;
            shift.employee = mEmployee;
            shift.business = mBusiness;
            shift.start = mStart;
            shift.end = mEnd;
            shift.firstname = fName;
            shift.lastname = lName;
            shift.id = mOid;
            return shift;
        }


    }



}
