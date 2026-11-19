package com.spring.dto;

public class APIResponse <T> {
    private String massege;
    private int status;
    private T data;


    public String getMassege() {
        return massege;
    }
    public void setMassege(String massege){
        this.massege = massege;
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public T getData(){
        return data;
    }
    public void setData(T data){
        this.data = data;
    }
}
