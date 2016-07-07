package com.example.junxu.newPoly;

import java.util.List;

/**
 * Created by junxu on 2016/7/6.
 */
public class ResultRespone {
    private String reason;  //返回状态
    private Result result;  //结果
    private int err_code;   //错误类型

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result{
        private String state;
        private List<TopnewInfo> data;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<TopnewInfo> getData() {
            return data;
        }

        public void setData(List<TopnewInfo> data) {
            this.data = data;
        }
    }
}
