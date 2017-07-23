package kiran541.ench.com.mathstest;

import android.os.Parcel;
import android.os.Parcelable;



public class Scores implements Parcelable {
    String uid;
    String id;
    String name;
    String email;
    String phno;
    String points;
    String total;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public static Creator<Scores> getCREATOR() {
        return CREATOR;
    }



    protected Scores(Parcel in) {
        uid = in.readString();
        id = in.readString();
        name = in.readString();
        email = in.readString();
        phno = in.readString();
        points = in.readString();
        total = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phno);
        dest.writeString(points);
        dest.writeString(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Scores> CREATOR = new Creator<Scores>() {
        @Override
        public Scores createFromParcel(Parcel in) {
            return new Scores(in);
        }

        @Override
        public Scores[] newArray(int size) {
            return new Scores[size];
        }
    };
}
