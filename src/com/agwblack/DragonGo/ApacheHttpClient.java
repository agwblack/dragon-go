package com.agwblack.DragonGo;

import  org.apache.http.client.HttpClient;
import  org.apache.http.client.ResponseHandler;
import  org.apache.http.client.methods.HttpGet;
import  org.apache.http.impl.client.DefaultHttpClient;
import  org.apache.http.impl.client.BasicResponseHandler;
import  org.apache.http.impl.client.AbstractHttpClient;
import  java.io.IOException;

public class ApacheHttpClient implements HttpClientWrapper {
  HttpClient client = new DefaultHttpClient();
  /*CookieStore cookieStore = new BasicCookieStore();
  HttpContext localContext = new BasicHttpContext();
  */
  public String sendMessage(String url) {
    //localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    HttpGet httpGet = new HttpGet(url);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    try {
      String responseBody = client.execute(httpGet, responseHandler);
      return responseBody;
    } catch (IOException e) {
      System.err.println(">>>>" + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }
}
