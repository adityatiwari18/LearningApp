package com.example.learningapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.Viewholder> {

    private static final String TAG = "MyAdapter";

    private List<String> Data;
    private Context mContext;

    public LessonAdapter(List<String> data, Context context) {
        Data = data;
        mContext = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
//        Log.d(TAG, "onCreateViewHolder: Starts");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.concept_layout, viewGroup, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
//        Log.d(TAG, "onBindViewHolder: Starts");
        final String list_items = Data.get(i);

        viewholder.Title.setText(list_items);
//        Log.d(TAG, "onBindViewHolder: Ends");
    }

    void setConcept(List<String> subjects){
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
        }
    }
}