package android.example.chitchat;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.example.chitchat.HistoryFragment.OnListFragmentInteractionListener;
import android.example.chitchat.dummy.DummyContent.DummyItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private final List<ChatMessage> mValues;
    private final OnListFragmentInteractionListener mListener;

    public HistoryRecyclerViewAdapter(ArrayList<ChatMessage> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mChatSender.setText(holder.mItem.getChatMessageSender());
        holder.mChatMessageText.setText(holder.mItem.getChatMessageText());
        holder.mChatSendTime.setText("(" + holder.mItem.getChatMessageSendTime() +")");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHistoryListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mChatSender;
        public final TextView mChatMessageText;
        public final TextView mChatSendTime;
        public ChatMessage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mChatSender = (TextView) view.findViewById(R.id.chatSender);
            mChatMessageText = (TextView) view.findViewById(R.id.chatMessageText);
            mChatSendTime = (TextView) view.findViewById(R.id.chatSendTime);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
