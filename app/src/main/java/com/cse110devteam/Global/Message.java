package com.cse110devteam.Global;

/**
 * Created by anthonyaltieri on 2/8/16.
 */
public class Message {
    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_LOG = 1;
    public static final int TYPE_ACTION = 2;

    private int mType;
    private String mMessage;
    private String mUsername;

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

    public static class Builder {
        private String mUsername;
        private String mMessage;

        public Builder() {
        }

        public Builder username(String username){
            mUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
            return this;
        }

        public Builder message(String message){
            mMessage = message;
            return this;
        }

        public Message build(){
            Message message = new Message();
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            return message;
        }
    }
}
