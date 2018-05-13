package com.prithwiraj.vamdiokerp.networks;

import org.json.JSONObject;

public interface VamHttpCommCallback {

    public void onSuccess(boolean status, int tag, JSONObject jsonResponse);

    public void onFailure(String error, int tag);
}
