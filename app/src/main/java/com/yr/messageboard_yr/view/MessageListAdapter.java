package com.yr.messageboard_yr.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yr.messageboard_yr.data.db.entity.Message;
import com.yr.messageboard_yr.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private final LayoutInflater mInflater;
    private List<Message> mMessages; // Cached copy of words
    private Context mContext;
    private ActionCallback mCallback;

    public MessageListAdapter(Context context, ActionCallback callback) {
        mContext = context;
        mCallback = callback;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.message_item, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (mMessages != null) {
            final Message current = mMessages.get(position);
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            holder.mName.setText(current.getUser());
            holder.mContent.setText(current.getContent());
            holder.mTime.setText(format.format(current.getTime()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(mContext)
                            .setTitle(current.getUser())
                            .setMessage(current.getContent() + "\n\n" + format.format(current.getTime()))
                            .setPositiveButton("編輯", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mCallback.onEdit(current.getId());
                                }
                            })
                            .setNegativeButton("刪除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mCallback.onDelete(current);
                                }
                            })
                            .setNeutralButton("OK", null)
                            .show();
                }
            });
        } else {
            holder.mName.setText("No Word");
        }
    }

    void setMessages(List<Message> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMessages != null)
            return mMessages.size();
        else return 0;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName, mContent, mTime;

        private MessageViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mContent = itemView.findViewById(R.id.content);
            mTime = itemView.findViewById(R.id.time);

        }
    }
}
