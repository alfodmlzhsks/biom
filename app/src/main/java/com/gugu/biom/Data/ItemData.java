package com.gugu.biom.Data;

/**
 * Created by Dandy on 2018-02-17.
 */

public class ItemData {

    private int type;
    public String strTitle;
    public String strDecs;
    public String strTitle_2;
    public String strTitle_3;
    public String strTitle_4;
    public String strTitle_5;



    public void setType(int type){
        this.type=type;
    }
    public void setstrTitle(String title){
        strTitle=title;
    }
    public void setstrDecs(String strDecs){
        this.strDecs=strDecs;
    }

    public void setstrTitle_2(String title){
        strTitle_2=title;
    }
    public void setstrTitle_3(String title){strTitle_3=title;}
    public void setstrTitle_4(String title){strTitle_4=title;}
    public void setstrTitle_5(String title){strTitle_5=title;}


    public int getType(){
        return this.type;
    }
    public String getStrTitle(){
        return this.strTitle;
    }
    public String getStrDecs(){
        return this.strDecs;
    }

    public String getStrTitle_2(){
        return this.strTitle_2;
    }
    public String getStrTitle_3(){ return this.strTitle_3;}
    public String getStrTitle_4(){ return this.strTitle_4;}
    public String getStrTitle_5(){ return this.strTitle_5;}
}


