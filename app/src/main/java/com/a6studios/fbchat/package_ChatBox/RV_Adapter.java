package com.a6studios.fbchat.package_ChatBox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HP on 3/31/2018.
 */

import com.a6studios.fbchat.R;

import java.util.ArrayList;
import java.util.List;

class sentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView stv;
    public sentViewHolder(View itemView) {
        super(itemView);
        stv = (TextView)itemView.findViewById(R.id.sent_msg);
        stv.setOnClickListener(this);
    }

    void bind(POJO_Message m)
    {
        stv.setText(m.getMessage());
    }

    @Override
    public void onClick(View v) {

    }
}

class recvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView rtv;
    public recvViewHolder(View itemView) {
        super(itemView);
        rtv = (TextView)itemView.findViewById(R.id.rec_msg);
        rtv.setOnClickListener(this);
    }

    void bind(POJO_Message m)
    {
        rtv.setText(m.getMessage());
    }

    @Override
    public void onClick(View v) {

    }
}

public class RV_Adapter extends RecyclerView.Adapter {
    List<POJO_Message> messageArrayList;
    Context mContext;
    final int SENT_MSG = 1;
    final int RECV_MSG = 2;

    public RV_Adapter(Context mContext) {
        this.mContext = mContext;
        messageArrayList = new ArrayList<>();
    }

    void addItem(POJO_Message m)
    {
        messageArrayList.add(m);
        notifyItemInserted(messageArrayList.size()-1);
    }

    private Context getContext(){
        return mContext;
    }

    @Override
    public int getItemViewType(int position) {
        POJO_Message m = messageArrayList.get(position);
        m.setSent();
        if (m.isSent())
            return SENT_MSG;
        else
            return RECV_MSG;
    }

    public void setMessageList(List<POJO_Message> pojo_messages)
    {
        messageArrayList = pojo_messages;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v;
        if(viewType == SENT_MSG)
        {
            v = inflater.inflate(R.layout.item_sent_msg,parent,false);
            return new sentViewHolder(v);
        }
        else
        {
            v = inflater.inflate(R.layout.item_rec_msg,parent,false);
            return new recvViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case SENT_MSG:
                sentViewHolder sentHolder = (sentViewHolder)holder;
                sentHolder.bind(messageArrayList.get(position));
                break;

            case RECV_MSG:
                recvViewHolder recvHolder = (recvViewHolder)holder;
                recvHolder.bind(messageArrayList.get(position));

        }

    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }
}
