package help.home.com.br.homehelp.webservices;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by Andre on 02/07/2015.
 */
public class HttpClientSingleton {

    private static final int JSON_CONNECTION_TIMEOUT = 6000;

    private static final int JSON_SOCKET_TIMEOUT = 20000;

    private static HttpClientSingleton instance;

    private HttpParams httpParameters ;

    private DefaultHttpClient httpclient;

    private void setTimeOut(HttpParams params){
        HttpConnectionParams.setConnectionTimeout(params, JSON_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, JSON_SOCKET_TIMEOUT);
    }

    private HttpClientSingleton() {
        httpParameters = new BasicHttpParams();
        setTimeOut(httpParameters);
        httpclient = new DefaultHttpClient(httpParameters);
    }

    public static DefaultHttpClient getHttpClientInstace(){
        if(instance==null)
            instance = new HttpClientSingleton();
        return instance.httpclient;
    }
}
