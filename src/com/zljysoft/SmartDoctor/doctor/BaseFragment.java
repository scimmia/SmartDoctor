package com.zljysoft.SmartDoctor.doctor;

import android.support.v4.app.Fragment;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 13-10-25
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseFragment extends Fragment {
    String identification;

    public BaseFragment(String identification) {
        this.identification = identification;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
