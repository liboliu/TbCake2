package com.popo.tbcake.dto;

/**
 * Created by popo on 14-12-10.
 */
public class BaseDto {
    public static final int SUCC=1;
    public static final int FAIL=0;
    public int result;
    public String errorMessge;

    public boolean isSucc(){
        return result==SUCC;
    }
}
