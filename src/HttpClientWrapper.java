import  org.apache.http.client.*;
import  org.apache.http.client.methods.*;

public interface HttpClientWrapper {
  int sendMessage(String url);
}

