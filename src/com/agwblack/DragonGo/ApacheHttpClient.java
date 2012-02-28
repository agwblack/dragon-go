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
  String responseBody;
  /*CookieStore cookieStore = new BasicCookieStore();
  HttpContext localContext = new BasicHttpContext();
  */

  public DGSEnumType.Error sendMessage(String url) {
    //localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
    HttpGet httpGet = new HttpGet(url);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    try {
      responseBody = client.execute(httpGet, responseHandler);
    } catch (IOException e) {
      System.err.println(">>>>" + e.getMessage());
      e.printStackTrace();
      return DGSEnumType.Error.CANNOT_FIND_SERVER;
    }
    return DGSEnumType.Error.NONE;
  }

  public String getMessageResponse() {
    return responseBody;
  }
}
