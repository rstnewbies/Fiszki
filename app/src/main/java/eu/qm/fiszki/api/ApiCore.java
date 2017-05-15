package eu.qm.fiszki.api;

import android.app.Activity;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import eu.qm.fiszki.AccountController;
import eu.qm.fiszki.R;
import eu.qm.fiszki.activity.ChangeActivityManager;

/**
 * Created by mBoiler on 14.05.2017.
 */

public class ApiCore {

    private final Activity mActivity;
    private RequestQueue mQueue;
    private JSONArray mResponse;

    public ApiCore(Activity activity) {
        mActivity = activity;
        mQueue = Volley.newRequestQueue(mActivity);
    }

    public void login(final String login, final String password) {
        String url = "http://api.fiszki.newbies.pl/v1/user?username="+login;
        final String pass = login+":"+password;

        JsonObjectRequest JSONRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                   if(response.get("username").equals(login)){
                       new AccountController(mActivity).set(pass);
                       new ChangeActivityManager(mActivity).resetMain();
                       Toast.makeText(mActivity,R.string.loged,Toast.LENGTH_SHORT).show();
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(mActivity, R.string.login_error,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String auth = "Basic "
                        + Base64.encodeToString(pass.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mQueue.add(JSONRequest);
    }
}
