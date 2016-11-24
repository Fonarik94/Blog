package com.company;

/**
 * Created by Ярослав on 08.11.2016.
 */
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
