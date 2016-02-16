package com.cse110devteam.Global;

/**
 * Created by anthonyaltieri on 2/8/16.
 */
public class Message {
    public static final int TYPE_MESSAGE_SELF = 0;
    public static final int TYPE_MESSAGE_OTHER = 1;

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
        private final int mType;
        private String mUsername;
        private String mMessage;

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

        public Message build(){
            Message message = new Message();
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            message.mType = mType;
            return message;
        }
    }
}
