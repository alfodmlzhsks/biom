package com.gugu.biom.Network;

/**
 * Created by gugu on 2018-02-12.
 */

public class DO {
    private String key = "MxYHBKCFORelL6cM7Z7VH4IYqjz0sK%2BY767A8oAC6mQqEWspAjUlWgUXkPFaws0QcGaxE3TyQYbr4sK8BzLJjA%3D%3D";
    private String base_date;
    private String base_time;
    private String nx;
    private String ny;
    private String numOfRows = "60";
    private String pageNo="1";
    private String pageSize="10";
    private String startPage="1";
    private String _type;
    private String action;

    public String getAction() {
        return action;
    }

    public void setNumOfRows(String numOfRows) {
        this.numOfRows = numOfRows;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStartPage() {
        return startPage;
    }

    public String getPageSize() {
        return pageSize;
    }


    public String getKey() {
        return key;
    }

    public String getBase_date() {
        return base_date;
    }

    public void setBase_date(String base_date) {
        this.base_date = base_date;
    }

    public String getBase_time() {
        return base_time;
    }

    public void setBase_time(String base_time) {
        this.base_time = base_time;
    }

    public String getNx() {
        return nx;
    }

    public void setNx(String nx) {
        this.nx = nx;
    }

    public String getNy() {
        return ny;
    }

    public void setNy(String ny) {
        this.ny = ny;
    }

    public String getNumOfRows() {
        return numOfRows;
    }

    public String getPageNo() {
        return pageNo;
    }


    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }
}
