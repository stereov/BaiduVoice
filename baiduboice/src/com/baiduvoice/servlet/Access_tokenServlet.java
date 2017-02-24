package com.baiduvoice.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.baiduvoice.httpfun.HttpRequestUtils;

/**
 * Created by lihui on 2017-02-24.
 */

@WebServlet("/Access_tokenServlet")
public class Access_tokenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String url;
    //private HttpRequestUtils httputil = new HttpRequestUtils();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Access_tokenServlet() {
        super();
        // TODO Auto-generated constructor stub
        //System.out.println("access_token class");
    }

    /**
     *
     */
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
    }

    /**
     *
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //System.out.println("dataservlet");
        String strApiid = request.getParameter("apiid");
        String strSecret = request.getParameter("apisecret");

        //url = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + strApiid +
        //		"&client_secret=" + strSecret;
        url = "http://openapi.baidu.com/oauth/2.0/token";

        String strParam = "grant_type=client_credentials&client_id=" + strApiid + "&client_secret=" + strSecret;
        JSONObject jsonResult = HttpRequestUtils.httpPost(url, strParam, false);

        //JSONObject jsonParam = null;
		/*JSONObject jsonParam = new JSONObject();
		try {
			jsonParam.put("grant_type", "client_credentials");
			jsonParam.put("client_id", strApiid);
			jsonParam.put("client_secret", strSecret);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpRequestUtils.httpPost(url, jsonParam, true);*/
        JSONObject result = new JSONObject();
        try {
            result = ParsaResult(jsonResult);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter pw = response.getWriter();
        pw.print(result.toString());
        pw.flush();
        pw.close();
        //response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private JSONObject ParsaResult(JSONObject jsonResult) throws JSONException {
        JSONObject result = new JSONObject();
        String key;
        Iterator<?> keys = jsonResult.keys();
        while(keys.hasNext()) {
            key = (String)keys.next();
            if(key.equals("access_token")) {
                result.put("result", "succ");
                result.put("content", jsonResult.getString(key));
                break;
            }
            if(key.equals("error")) {
                result.put("result", "fail");
                result.put("content", jsonResult.getString(key));
                break;
            }
        }

        return result;
    }
}