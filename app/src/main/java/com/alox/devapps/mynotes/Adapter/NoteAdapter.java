package com.alox.devapps.mynotes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alox.devapps.mynotes.Database.Notes;
import com.alox.devapps.mynotes.NoteActivity;
import com.alox.devapps.mynotes.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Notes> notes;
    private Context mContext;

    public NoteAdapter(Context context,List<Notes> notes) {
        this.notes = notes;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (notes.get(position)!=null) {
            final String title = notes.get(position).getTitleNote();
            final String desc = notes.get(position).getDescNote();
            final String date = notes.get(position).getDateNote();
            holder.title.setText(title);
            holder.desc.setText(desc);
            holder.date.setText(date);
        } else {
            holder.title.setText("No Notes");
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent note = new Intent(mContext, NoteActivity.class);
                NoteActivity.notes = notes.get(position);
                NoteActivity.position = position;
                note.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(note);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public Notes getNote(int position) {
        return notes.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc,date;
        FrameLayout card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitleCard);
            desc = itemView.findViewById(R.id.descNoteCard);
            date = itemView.findViewById(R.id.noteDate);
            card = itemView.findViewById(R.id.cardNote);
        }
    }
}
