import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

@Slf4j
public class HttpClientTest {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            log.info("响应：{}", responseEntity.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if (httpClient != null)
                    httpClient.close();
                if (response != null)
                    response.close();
            }catch (IOException e){
                log.error("", e);
            }
        }
    }

}
