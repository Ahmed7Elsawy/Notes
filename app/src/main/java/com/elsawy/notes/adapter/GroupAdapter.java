package com.elsawy.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.elsawy.notes.R;
import com.elsawy.notes.models.Group;


public class GroupAdapter extends ListAdapter<Group,GroupAdapter.GroupViewHolder> {

    private OnItemClickListener listener;

    public GroupAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Group> DIFF_CALLBACK = new DiffUtil.ItemCallback<Group>() {
        @Override
        public boolean areItemsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.getGid() == newItem.getGid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Group oldItem, @NonNull Group newItem) {
            return oldItem.equals(newItem);
        }
    };


    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        final Group group = getItem(position);

        holder.idTextView.setText(String.valueOf(group.getGid()));
        holder.titleTextView.setText(group.getGroupName());

    }

    public Group getGroupAtPosition(int position) {
        return getItem(position);
    }


    class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView idTextView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.group_id_textview);
            titleTextView = itemView.findViewById(R.id.group_title_textview);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Group group);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
