package com.example.NotesApp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NotesApp.Database.Note;
import com.example.NotesApp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> implements Filterable {    //Indicate Adapter and Holder
    private List<Note> notes = new ArrayList<>();
    private List<Note> newList;
    private OnItemClickListener listener;

    //Creat holder
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

//Give recycle holder & position
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewTimeStamp.setText(currentNote.getTimeStamp());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
        newList = new ArrayList<>(notes);
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<Note> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newList);
            } else
                {
                for (Note item: newList) {
                    if (item.getTitle().toLowerCase().contains(constraint.toString().toLowerCase().trim()))
                    {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notes.clear();
            notes.addAll((Collection<? extends Note>) results.values);
            notifyDataSetChanged();
        }
    };

//Include the view item
    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewPriority;
        private TextView textViewTimeStamp;

//Relationship of view
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewTimeStamp = itemView.findViewById(R.id.text_view_timeStamp);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
//Edit note listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemCLick(notes.get(position));
                    }
                }
            });

        }
    }
//Edit note
    public interface OnItemClickListener {
        void onItemCLick(Note note);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

}
