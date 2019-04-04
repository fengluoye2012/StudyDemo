package com.mydesign.modes.aidl_test;


import android.os.RemoteException;

import com.mydesign.modes.IMyAidlInterface;

public class IRemoteService extends IMyAidlInterface.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
