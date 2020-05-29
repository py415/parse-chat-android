package com.yuphilip.parsechat.controller.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yuphilip.parsechat.R;
import com.yuphilip.parsechat.model.Message;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final List<Message> mMessages;
    private final Context mContext;
    private final String mUserId;

    public ChatAdapter(Context context, String userId, List<Message> messages) {

        mMessages = messages;
        this.mUserId = userId;
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_chat, parent, false);

        return new ViewHolder(contactView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Message message = mMessages.get(position);

        final boolean isMe = message.getUserId() != null && message.getUserId().equals(mUserId);

        if (isMe) {
            final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.START_OF, R.id.ivProfileMe);
            params.addRule(RelativeLayout.LEFT_OF, R.id.ivProfileMe);
            params.setMargins(8, 32, 8, 0);

            holder.imageMe.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.GONE);
            holder.body.setBackgroundResource(R.drawable.tv_bubble);
            holder.body.setLayoutParams(params);
        } else {
            final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.END_OF, R.id.ivProfileOther);
            params.addRule(RelativeLayout.RIGHT_OF, R.id.ivProfileOther);
            params.setMargins(8, 32, 8, 0);

            holder.imageOther.setVisibility(View.VISIBLE);
            holder.imageMe.setVisibility(View.GONE);
            holder.body.setBackgroundResource(R.drawable.tv_bubble_other);
            holder.body.setLayoutParams(params);
        }

        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;

        Glide.with(mContext)
                .load(getProfileUrl(message.getUserId()))
                .apply(RequestOptions.circleCropTransform())
                .into(profileView);
        holder.body.setText(message.getBody());

    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(final String userId) {

        String hex = "";

        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "https://www.gravatar.com/avatar/" + hex + "?d=identicon";

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageOther;
        final ImageView imageMe;
        final TextView body;

        ViewHolder(View itemView) {

            super(itemView);

            imageOther = itemView.findViewById(R.id.ivProfileOther);
            imageMe = itemView.findViewById(R.id.ivProfileMe);
            body = itemView.findViewById(R.id.tvBody);

        }

    }

}
