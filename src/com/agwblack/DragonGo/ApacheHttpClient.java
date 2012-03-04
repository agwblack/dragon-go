package com.agwblack.DragonGo;

import  org.apache.http.client.HttpClient;
import  org.apache.http.client.ResponseHandler;
import  org.apache.http.client.methods.HttpGet;
import  org.apache.http.impl.client.DefaultHttpClient;
import  org.apache.http.impl.client.BasicResponseHandler;
import  org.apache.http.impl.client.AbstractHttpClient;
import  org.apache.http.cookie.Cookie;
import  java.io.IOException;
import  java.util.List;

public class ApacheHttpClient implements HttpClientWrapper {
  DefaultHttpClient client = new DefaultHttpClient();
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

  public Cookie[] getCookies() {
    List<Cookie> cs = client.getCookieStore().getCookies();
    Cookie[] cookies = new Cookie[cs.size()];
    for (int i = 0; i != cs.size(); ++i) {
      cookies[i] = cs.get(i);
    }
    return cookies;
  }

  public void addCookie(Cookie ck) {
    client.getCookieStore().addCookie(ck);
  }
}
