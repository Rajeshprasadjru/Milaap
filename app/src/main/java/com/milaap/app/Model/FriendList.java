package com.milaap.app.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendList implements Parcelable {

    public String date_created;

    public String user_id;
    public String status;
    public String time_stamp;


    public FriendList() {

    }

    public FriendList(String date_created, String user_id, String status, String time_stamp) {
        this.date_created = date_created;
        this.user_id = user_id;
        this.status = status;
        this.time_stamp = time_stamp;
    }


    protected FriendList(Parcel in) {
        date_created = in.readString();
        user_id = in.readString();
        status = in.readString();
        time_stamp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_created);
        dest.writeString(user_id);
        dest.writeString(status);
        dest.writeString(time_stamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FriendList> CREATOR = new Creator<FriendList>() {
        @Override
        public FriendList createFromParcel(Parcel in) {
            return new FriendList(in);
        }

        @Override
        public FriendList[] newArray(int size) {
            return new FriendList[size];
        }
    };

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    @Override
    public String toString() {
        return "FriendList{" +
                "date_created='" + date_created + '\'' +
                ", user_id='" + user_id + '\'' +
                ", status='" + status + '\'' +
                ", time_stamp='" + time_stamp + '\'' +
                '}';
    }
}
