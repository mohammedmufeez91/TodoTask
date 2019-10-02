package com.mufeez.taskzero.data.repository;

import android.content.Context;
import android.util.Log;

import com.mufeez.taskzero.data.model.Task;
import com.mufeez.taskzero.root.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mufeez kashimji on 27-09-19.
 */

public class TaskRepository implements TaskRepositoryHelper{

    private ArrayList<TaskRepoObserver> mObservers;

    private TaskRepository(Context context){
        mDatabase = AppDatabase.getInstance(context);
        mObservers = new ArrayList<>();
    }
    private static TaskRepository instance;

    private AppDatabase mDatabase;
    public static TaskRepository getInstance(Context context){
        if(instance == null){
            instance = new TaskRepository(context);
        }
        return instance;
    }

    public void addObserver(TaskRepoObserver observer){
        mObservers.add(observer);
    }

    public void removeObserver(TaskRepoObserver observer){
        if(mObservers.contains(observer))
            mObservers.remove(observer);
    }

    public void notifyAddTaskObservers(Task task){
        for(TaskRepoObserver observer : mObservers){
            observer.onTaskAdded(task);
        }
    }

    public void notifyDeletedTaskObservers(Task task){
        for(TaskRepoObserver observer : mObservers){
            observer.onTaskDeleted(task);
        }
    }

    public void notifyCompletedTaskObservers(Task task){
        for(TaskRepoObserver observer : mObservers){
            observer.onTaskCompleted(task);
        }
    }

    @Override
    public void addTask(Task task) {
        mDatabase.taskDao().addTask(task);
        notifyAddTaskObservers(task);
    }

    @Override
    public void deleteTask(Task task) {
        Log.i("Task-db","inside-repo1");
        mDatabase.taskDao().deleteTask(task);
        notifyDeletedTaskObservers(task);
    }

    @Override
    public void completeTask(Task task) {
        Log.i("Task-db","inside-repo1");
        Log.i("Task-db","iscompleted"+task.getIscompleted());
        Log.i("Task-db","uid"+task.getUid());
        mDatabase.taskDao().updateItem(task);
        notifyCompletedTaskObservers(task);
    }


    @Override
    public List<Task> getAllTasks() {
        return mDatabase.taskDao().getAllTasks();
    }

    public interface TaskRepoObserver{
        void onTaskAdded(Task task);
        void onTaskDeleted(Task task);
        void onTaskCompleted(Task task);
    }
}
