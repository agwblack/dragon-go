package com.agwblack.DragonGo;

import  org.apache.http.client.*;
import  org.apache.http.client.methods.*;

public interface HttpClientWrapper {
  String sendMessage(String url);
}


