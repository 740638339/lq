package com.lq.yl.product.count.app.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;

public class SvcCallHelper {

	private static SvcCallHelper INSTANCE;
	private static Object LOCK = new Object();

	public static final String VolleyRequestTag = "VolleyPatterns";

	private RequestQueue      mVolleyReqQueue;
	private JsonObjectRequest mJSONObjectRequest = null;
    private StringRequest     mStringRequest     = null;
    @SuppressWarnings("unused")
	private JSONObject        mJSONRspObj;
	@SuppressWarnings("unused")
	private JSONArray         mJSONArray;

	private Context mCtx;

	private SvcCallHelper(Context ctx){
		this.mCtx = ctx;
	}

	public static SvcCallHelper GetInstance(Context ctx){
		if(INSTANCE == null){
			synchronized(LOCK){
				INSTANCE = new SvcCallHelper(ctx);
			}
		}
		return INSTANCE;
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	private RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (this.mVolleyReqQueue == null) {
			this.mVolleyReqQueue = Volley.newRequestQueue(this.mCtx);
		}

		return this.mVolleyReqQueue;
	}

//	/**
//	 * Adds the specified request to the global queue, if tag is specified then
//	 * it is used else Default TAG is used.
//	 * 
//	 * @param req
//	 * @param tag
//	 */
//	private <T> void addToRequestQueue(Request<T> req, String tag) {
//		// set the default tag if tag is empty
//		req.setTag(TextUtils.isEmpty(tag) ? VolleyRequestTag : tag);
//
//		VolleyLog.d("Adding request to queue: %s", req.getUrl());
//
//		getRequestQueue().add(req);
//	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 *
	 * @param req
	 */
	private <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(VolleyRequestTag);

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 *
	 * @param tag
	 */
	@SuppressWarnings("unused")
	private void cancelPendingRequests(Object tag) {
		if (this.mVolleyReqQueue != null) {
			this.mVolleyReqQueue.cancelAll(tag);
		}
	}

	public void postStringRequest(String url,
			Response.Listener<String> scsLstn,
			Response.ErrorListener flrLstn,
			final Map<String, String> params){
		this.mStringRequest = new StringRequest(Method.POST, url, scsLstn, flrLstn) {

			@Override
			protected Map<String, String> getParams() {
				return params;
			}
		};
		this.mStringRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1.0f));
		this.addToRequestQueue(this.mStringRequest);
	}

	public void getStringRequest(String url,
			Response.Listener<String> scsLstn,
			Response.ErrorListener flrLstn,
			final Map<String, String> params){

		try {
			this.mStringRequest = new StringRequest(Method.GET, url + getHttpParams(params), scsLstn, flrLstn);
			this.mStringRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1.0f));
			this.addToRequestQueue(this.mStringRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getStringRequest(String url,
			Response.Listener<String> scsLstn,
			Response.ErrorListener flrLstn,
			final Map<String, String> params,RetryPolicy retryPolicy){

		try {
			this.mStringRequest = new StringRequest(Method.GET, url + getHttpParams(params), scsLstn, flrLstn);
			this.mStringRequest.setRetryPolicy(retryPolicy);
			this.addToRequestQueue(this.mStringRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void postJSONObjectRequest(String url, Response.Listener<JSONObject> scsLstn, Response.ErrorListener flrLstn){
		this.mJSONObjectRequest = new JsonObjectRequest(Method.POST, url,
				null, scsLstn, flrLstn);
		this.mJSONObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1.0f));
		this.addToRequestQueue(this.mJSONObjectRequest);
	}

	public static String getHttpParams(Map<String,String> params) throws IOException{
		String renParams = "";
		if(params != null){
			StringBuffer buffer = new StringBuffer();
			Set<String> paramsSet = params.keySet();
			if(paramsSet.size() > 0){
				buffer.append("?");
				for(String key:params.keySet()){
					buffer.append(key + "=" + URLEncoder.encode(params.get(key),"utf-8")).append("&");
				}
				renParams = buffer.toString();
				renParams = renParams.substring(0, renParams.length()-1);
			}
		}
		return renParams;
	}
}
