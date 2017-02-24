package com.baiduvoice.servlet;

import com.baiduvoice.httpfun.HttpRequestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;

/**
 * Created by lihui on 2017-02-24.
 */
@WebServlet("/Voice_generationServlet")

public class Voice_generationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String url;
    String src;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Voice_generationServlet() {
        super();
        // TODO Auto-generated constructor stub

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //System.out.println("voice");
        String strToken = request.getParameter("token");
        String strAppid = request.getParameter("appid");
        String strContent = request.getParameter("content_text");

        //url = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + strApiid +
        //		"&client_secret=" + strSecret;
        url = "http://tsn.baidu.com/text2audio";

        String strParam = "tex=" + strContent + "&lan=zh&cuid=" + strAppid + "&ctp=1&tok=" + strToken;

        src = url + "?" + strParam;
        src = URLDecoder.decode(src,"UTF-8");
        System.out.println(src);

        JSONObject jsonResult = HttpRequestUtils.httpPostVoiceGen(url, strParam, false);

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
            if(key.equals("succ")) {
                result.put("result", "succ");
                result.put("content", src);
                break;
            }
            if(key.equals("err_no")) {
                result.put("result", "fail");
                result.put("content", jsonResult.getString(key));
                break;
            }
        }

        return result;
    }
}