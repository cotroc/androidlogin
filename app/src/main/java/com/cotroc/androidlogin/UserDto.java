package com.cotroc.androidlogin;

/**
 * Created by Cotroc on 22/08/2017.
 */

public class UserDto {

    private int id;
    private String name;
    private String pwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public UserDto() {

    }

    public UserDto(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
    @Override
    public String toString() {
        return "{\"id\":\""+ id +"\"," +
                "\"name\":\""+name+"\"," +
                "\"pwd\":\""+pwd+"\"}";
    }
}
