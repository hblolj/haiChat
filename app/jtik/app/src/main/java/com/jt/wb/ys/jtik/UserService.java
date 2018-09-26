package com.jt.wb.ys.jtik;

public class UserService implements IUserService{

    @Override
    public String search(int code){
        return "User: " + code;
    }
}
