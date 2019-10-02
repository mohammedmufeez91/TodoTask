package com.mufeez.taskzero.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.mufeez.taskzero.R;
import com.mufeez.taskzero.utils.TimeUtils;

import java.util.Date;

/**
 * Created by Mufeez kashimji on 27-09-19.
 */
 @Entity(tableName = "task")
public class Task implements Parcelable {

    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_NORMAL = 2;
    public static final int PRIORITY_HIGH = 3;

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String title;
    public long createdAt;
    public String body;
    public int priority;
    public boolean iscompleted;


    public Task(String title, String body, int priority,boolean iscompleted){
        this.title = title;
        this.body = body;
        this.iscompleted=iscompleted;
        if(priority > 3 || priority < 1)
            this.priority = PRIORITY_DEFAULT;
        else
            this.priority = priority;
        this.createdAt = new Date().getTime();
    }

   /* public Task(int uid,String title, String body, int priority,boolean iscompleted){
        this.title = title;
        this.uid=uid;
        this.body = body;
        this.iscompleted=iscompleted;
        if(priority > 3 || priority < 1)
            this.priority = PRIORITY_DEFAULT;
        else
            this.priority = priority;
        this.createdAt = new Date().getTime();
    }*/

    protected Task(Parcel in) {
        uid = in.readInt();
        title = in.readString();
        createdAt = in.readLong();
        body = in.readString();
        priority = in.readInt();
        iscompleted = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public int getPriority() {
        return priority;
    }

    public Boolean getIscompleted() {
        return iscompleted;
    }

    public void setIscompleted(Boolean iscompleted) {
        this.iscompleted = iscompleted;
    }

    public String getCreatedAtText(){
        return com.mufeez.taskzero.utils.TimeUtils.getFormattedTime(createdAt, TimeUtils.FULL_TIME_FORMAT);
    }

    public int getBackgroundColor(){
        switch (priority){
            case PRIORITY_LOW:
                return R.color.priority_low;
            case PRIORITY_NORMAL:
                return  R.color.priority_normal;
            case PRIORITY_HIGH:
                return R.color.priority_high;
            default:
                return R.color.priority_default;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeString(title);
        parcel.writeLong(createdAt);
        parcel.writeString(body);
        parcel.writeInt(priority);
        parcel.writeByte((byte) (iscompleted ? 1 : 0));
    }
}
