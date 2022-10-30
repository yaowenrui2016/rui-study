package indi.rui.study.unittest.auto;

import indi.rui.study.unittest.dto.MkLoginResult;
import indi.rui.study.unittest.support.MkLoginHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author: yaowr
 * @create: 2022-10-14
 */
@Slf4j
public class AutoAdminE2EUploadFile {

    private static final String RSA_PUB_KEY = "30819f300d06092a864886f70d010101050003818d0030818902818100a55ea926840ebb0099f4f671d4f461ed7f5d646ffe6a390ff8bd3c267e2aa65216b734cc2a0dfab6bb145c9d848cc6fb9acdedbece12ad0ff6b36fecfac70552665cc2e7af8ea3669be9e86b265afbaf3806118bc77da43051d78b8fe46aad724587dc2f53c72e96776ad0833f0f0e8d4fc43cafe474756d8b6bae92abb9a1850203010001";

    private static File uploadFile = new File("D:\\project\\rui-study\\downloadTemp\\审批模板_20221014150010.zip");

    public static void main(String[] args) throws IOException {
        MkLoginResult loginResult = MkLoginHelper.doLoginWithPubKey("http://127.0.0.1:8040", "sysadmin", "Password_1", RSA_PUB_KEY);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8040/data/sys-admin/e2eTools/parse");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setSocketTimeout(60000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("X-AUTH-TOKEN", loginResult.getXAuthToken());
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addBinaryBody("multipartFile", uploadFile)
                .addTextBody("filename", URLEncoder.encode(uploadFile.getName(), "utf-8"), ContentType.TEXT_PLAIN)
                .build();
        httpPost.setEntity(httpEntity);
        String result = httpClient.execute(httpPost, new BasicResponseHandler());
        log.info("update result={}", result);
    }
}
