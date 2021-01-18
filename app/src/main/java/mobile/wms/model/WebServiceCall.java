package mobile.wms.model;


import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import mobile.wms.listeners.WebServiceListener;

public class WebServiceCall  {


    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private WebServiceListener listener;

    public void callGetMethod(){

    }

    public void callPostMethod(Context context, String url, Map<String, String> params){

        final ProgressDialog progressDialog  = new ProgressDialog(context);
        progressDialog.setMessage("Asteptati....");
        progressDialog.show();

        mRequestQueue = Volley.newRequestQueue(context);

        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public void onResponse(String response) {

                if (listener != null)
                    listener.onCallComplete(response.toString());

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {

                if (listener != null)
                    listener.onCallComplete("-1");

                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> callParams = new HashMap<String, String>();

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    callParams.put(entry.getKey(), entry.getValue());
                }

                return callParams;
            }
        };

        mRequestQueue.add(mStringRequest);

    }

    public void setServiceListener(WebServiceListener listener){
        this.listener = listener;

    }

}
