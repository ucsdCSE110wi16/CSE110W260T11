package com.cse110devteam.Models;

import android.graphics.Typeface;

/**
 * Created by anthonyaltieri on 2/8/16.
 */
public class Message {
    public static final int TYPE_MESSAGE_SELF = 0;
    public static final int TYPE_MESSAGE_OTHER = 1;

    private int mType;
    private String mMessage;
    private String mUsername;
    private String mTime;
    private Typeface tfUsername;
    private Typeface tfMessage;
    private Typeface tfTime;

    private Message() {}

    private int getType(){
        return mType;
    }

    public String getMessage(){
        return mMessage;
    }

    public String getUsername(){
        return mUsername;
    }

    public String getTime()
    {
        return mTime;
    }

    public Typeface getTfUsername()
    {
        return tfUsername;
    }

    public Typeface getTfMessage()
    {
        return tfMessage;
    }

    public Typeface getTfTime()
    {
        return tfTime;
    }

    public static class Builder {
        private final int mType;
        private String mUsername;
        private String mMessage;
        private String mTime;
        private Typeface typefaceUsername;
        private Typeface typefaceMessage;
        private Typeface typefaceTime;

        public Builder(int type) {
            mType = type;
        }

        public Builder username(String username){
            mUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
            return this;
        }

        public Builder message(String message){
            mMessage = message;
            return this;
        }

        public Builder time(String time){
            mTime = time;
            return this;
        }

        public Builder typefaceUsername( Typeface tf )
        {
            typefaceUsername = tf;
            return this;
        }

        public Builder typefaceMessage( Typeface tf )
        {
            typefaceMessage = tf;
            return this;
        }

        public Builder typefaceTime( Typeface tf )
        {
            typefaceTime = tf;
            return this;
        }

        public Message build(){
            Message message = new Message();
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            message.mTime = mTime;
            message.mType = mType;
            message.tfMessage = typefaceMessage;
            message.tfUsername = typefaceUsername;
            message.tfTime = typefaceTime;
            return message;
        }


    }
}
