package com.cse110devteam.Global;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cse110devteam.Models.Message;
import com.cse110devteam.R;

import java.util.List;

/**
 * Created by anthonyaltieri on 2/8/16.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;
    private int mUsernameColor;

    public MessageAdapter(Context context, List<Message> messages) {
        mMessages = messages;
        mUsernameColor = context.getResources().getColor(R.color.usernamecolor_default);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.chat_message;
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        holder.setMessage(message.getMessage());
        holder.setUsername(message.getUsername());
        holder.setTime( message.getTime() );
        holder.setUsernameTypeface( message.getTfUsername() );
        holder.setMessageTypeface( message.getTfMessage() );
        holder.setTimeTypeface( message.getTfTime() );
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUsernameView;
        private TextView mMessageView;
        private TextView mTimeView;
        private Typeface usernameTypeface;
        private Typeface messageTypeface;
        private Typeface timeTypeface;

        public ViewHolder(View itemView) {
            super(itemView);

            mUsernameView = (TextView) itemView.findViewById( R.id.sender_username );
            mUsernameView.setTypeface( usernameTypeface );
            mMessageView = (TextView) itemView.findViewById( R.id.message );
            mMessageView.setTypeface( messageTypeface );
            mTimeView = (TextView) itemView.findViewById( R.id.message_timestamp );
            mTimeView.setTypeface( timeTypeface );
        }

        public void setUsername(String username) {
            if (mUsernameView == null) return;
            mUsernameView.setText(username);
            mUsernameView.setTextColor(getUsernameColor(username));
        }

        public void setMessage(String message) {
            if (mMessageView == null) return;
            mMessageView.setText(message);
        }

        public void setTime( String time )
        {
            if ( time == null ) return;
            mTimeView.setText( time );
        }

        public void setUsernameTypeface( Typeface tf )
        {
            if ( tf == null ) return;
            usernameTypeface = tf;
        }

        public void setMessageTypeface( Typeface tf )
        {
            if ( tf == null ) return;
            messageTypeface = tf;
        }

        public void setTimeTypeface( Typeface tf )
        {
            if ( tf == null ) return;
            timeTypeface = tf;
        }

        private int getUsernameColor(String username) {
            // TODO: Change color depending on self/employee/manager
            return mUsernameColor;
        }

    }
}
