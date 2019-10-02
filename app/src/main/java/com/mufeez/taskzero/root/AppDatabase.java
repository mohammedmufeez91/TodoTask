package com.mufeez.taskzero.root;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import com.mufeez.taskzero.data.model.Task;
import com.mufeez.taskzero.data.model.TaskDao;

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

@Database(entities = {Task.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    protected AppDatabase(){}
    private static AppDatabase instance;

    public abstract TaskDao taskDao();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"task-database").
                    allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
