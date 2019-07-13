package com.example.yingwu.utils;

import java.text.SimpleDateFormat;

public class IdGenerator  {
    public static volatile int Guid=100;
    public static String getId(){
        IdGenerator.Guid+=1;
        long now=System.currentTimeMillis();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");
        String time=dateFormat.format(now);
        String info=now+"";
        int ran=0;
        if(IdGenerator.Guid>999){
            IdGenerator.Guid=100;
        }
        ran=IdGenerator.Guid;
        return time+info.substring(2,info.length())+ran;
    }
}
