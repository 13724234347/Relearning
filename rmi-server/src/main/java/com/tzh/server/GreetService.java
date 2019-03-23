package com.tzh.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GreetService extends Remote {

    public User get()throws RemoteException;


}
