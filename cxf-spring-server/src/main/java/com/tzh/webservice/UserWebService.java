package com.tzh.webservice;


import com.tzh.entity.User;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserWebService {

    public List<User> queryUser();


}
