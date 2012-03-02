package com.agwblack.DragonGo;

import  org.apache.http.client.*;
import  org.apache.http.client.methods.*;
import  org.apache.http.cookie.Cookie;

import java.io.IOException;

public interface HttpClientWrapper {
  DGSEnumType.Error sendMessage(String url);

  String getMessageResponse();

  Cookie[] getCookies();
}


