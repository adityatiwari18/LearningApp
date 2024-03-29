package com.example.learningapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.Viewholder> {

    private static final String TAG = "MyAdapter";

    private List<String> Data;
    private Context mContext;

    public SubjectsAdapter(List<String> data, Context context) {
        Data = data;
        mContext = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
//        Log.d(TAG, "onCreateViewHolder: Starts");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_layout, viewGroup, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
//        Log.d(TAG, "onBindViewHolder: Starts");
        final String list_items = Data.get(i);

        viewholder.Title.setText(list_items);
//        Log.d(TAG, "onBindViewHolder: Ends");
    }

    void setSubjects(List<String> subjects){
        Data = subjects;
    }


    @Override
    public int getItemCount() {
        return Data.size();
    }


    static class Viewholder extends RecyclerView.ViewHolder {

        TextView Title;
        CardView Subject;
        Context mContext;

        public Viewholder(@NonNull final View itemView) {
            super(itemView);

            this.Title = (TextView) itemView.findViewById(R.id.lesson);
            this.Subject = (CardView) itemView.findViewById(R.id.Lesson);
            this.mContext = itemView.getContext();
            Subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), CirriculumContent.class);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
