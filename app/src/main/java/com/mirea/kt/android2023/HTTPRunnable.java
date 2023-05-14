package com.mirea.kt.android2023;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class HTTPRunnable implements Runnable {

    private String address;
    private HashMap<String, String> reqBody;
    private String respBody;

    public HTTPRunnable(String address, HashMap<String, String> reqBody) {
        this.address = address;
        this.reqBody = reqBody;
    }

    public String getRespBody() {
        return respBody;
    }

    public void run() {
        if (address != null) {
            try{
                URL url = new URL(address);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("POST");
                httpConnection.setDoOutput(true);
                OutputStreamWriter osw = new OutputStreamWriter(httpConnection.getOutputStream());
                osw.write(generateStringBody());
                osw.flush();
                int responseCode = httpConnection.getResponseCode();
                System.out.println("Response code: " + responseCode);
                if (responseCode == 200) {
                    InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String currentLine;
                    StringBuilder sbResponse = new StringBuilder();
                    while ((currentLine = br.readLine()) != null) {
                        sbResponse.append(currentLine);
                    }
                    respBody = sbResponse.toString();
                } else {
                    Log.e("uiop", "Bad response code");
                }
            } catch (MalformedURLException e) {
                Log.e("uiop", e.getMessage());
            } catch (IOException e) {
                Log.e("uiop", e.getMessage());
            }
        }
    }

    private String generateStringBody() {
        StringBuilder sbParams = new StringBuilder();
        if (reqBody!=null && !reqBody.isEmpty()) {
            int i=0;
            for (String key : reqBody.keySet()) {
                try {
                    if (i!=0) {
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=").append(URLEncoder.encode(reqBody.get(key), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.e("uiop", e.getMessage());
                }
                i++;
            }
        }
        return sbParams.toString();
    }
}