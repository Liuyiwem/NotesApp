package com.example.NotesApp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;


    }


//    Add note when database was created.

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){        //DB Created populate data
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void,Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){

            noteDao = db.noteDao();


        }

        @Override
        protected Void doInBackground(Void... voids) {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm");
//            Date dt = new Date();
//            String currentTime = sdf.format(dt);

            noteDao.insert(new Note("Title 1", "Description 1","mmmm",1));
//            noteDao.insert(new Note("Title 2", "Description 2",currentTime,2));
//            noteDao.insert(new Note("Title 3", "Description 3",currentTime,3));
            return null;
        }
    }

}
