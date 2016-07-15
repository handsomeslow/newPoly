package com.example.junxu.newPoly;

import java.util.List;

/**
 * Created by junxu on 2016/7/14.
 */
public class TohDetail {
    int error_code;
    String reason;
    List<TohInfo>  result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<TohInfo> getResult() {
        return result;
    }

    public void setResult(List<TohInfo> result) {
        this.result = result;
    }

    public static class TohInfo{
        int day;
        String des;
        int id;
        String lunar;
        int month;
        String pic;
        String title;
        int year;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLunar() {
            return lunar;
        }

        public void setLunar(String lunar) {
            this.lunar = lunar;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public String toString() {
            return "TohInfo{" +
                    "day=" + day +
                    ", des='" + des + '\'' +
                    ", id=" + id +
                    ", lunar='" + lunar + '\'' +
                    ", month=" + month +
                    ", pic='" + pic + '\'' +
                    ", title='" + title + '\'' +
                    ", year=" + year +
                    '}';
        }
    }
}
