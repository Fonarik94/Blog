package com.fonarik94.utils;

public class ClassNameUtil {
    private ClassNameUtil(){}
    public static String getCurentClassName(){
        try{
            throw new RuntimeException();
        }
        catch (RuntimeException e){
            return e.getStackTrace()[1].getClassName();
        }
    }
}
