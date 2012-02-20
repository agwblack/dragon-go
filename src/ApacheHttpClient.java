import  org.apache.http.client.HttpClient;
import  org.apache.http.client.ResponseHandler;
import  org.apache.http.client.methods.HttpGet;
import  org.apache.http.impl.client.DefaultHttpClient;
import  org.apache.http.impl.client.BasicResponseHandler;
import  org.apache.http.impl.client.AbstractHttpClient;
import  java.io.IOException;

public class ApacheHttpClient implements HttpClientWrapper {
  HttpClient client = new DefaultHttpClient();
  
  public int sendMessage(String url) {
    HttpGet httpGet = new HttpGet(url);
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    try {
      String responseBody = client.execute(httpGet, responseHandler);
      System.out.println(responseBody);
    } catch (IOException e) {
      System.err.println(">>>>" + e.getMessage());
      e.printStackTrace();
    }
    return 0;
  }
}
