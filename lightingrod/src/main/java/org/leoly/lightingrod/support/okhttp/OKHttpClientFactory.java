/**
 * copywrite 2015-2020 智慧享联
 * 不能修改和删除上面的版权声明
 * 此代码属于智慧享联编写，在未经允许的情况下不得传播复制
 * OKHttpClientFactory.java
 *
 * @Date 2018年5月4日 上午9:32:56
 * guguihe
 */
package org.leoly.lightingrod.support.okhttp;

import okhttp3.*;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * TODO:
 *
 * @author guguihe
 * @Date 2018年5月4日 上午9:32:56
 */
public class OKHttpClientFactory {

    private OKHttpClientFactory() {
    }

    private static class OKHttpClientHelper {
        private static OkHttpClient HTTPCLIENT_INSTANCE = getHttpClient();


        private static final class GzipRequestInterceptor implements Interceptor {

            private final Logger logger = LoggerFactory.getLogger(getClass());

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                    return chain.proceed(originalRequest);
                }

                RequestBody originalBody = originalRequest.body();
                if (originalBody.contentLength() > 1024) {
                    logger.info("原始请求节点数：" + originalBody.contentLength() + "，使用GZIP压缩传输数据！");
                    Request compressedRequest = originalRequest.newBuilder()
                            .header("Content-Encoding", "gzip")
                            .method(originalRequest.method(), gzip(originalBody))
                            .build();
                    return chain.proceed(compressedRequest);
                }

                return chain.proceed(originalRequest);
            }

            private RequestBody gzip(final RequestBody body) {
                return new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return body.contentType();
                    }

                    @Override
                    public long contentLength() {
                        return -1; // 无法提前知道压缩后的数据大小
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                        body.writeTo(gzipSink);
                        gzipSink.close();
                    }
                };
            }
        }

        private static OkHttpClient getHttpClient() {
            SSLSocketFactory sslSocketFactory = null;
            X509TrustManager trsutManager = null;
            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                trsutManager = new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
                };

                sc.init(null, new TrustManager[]{trsutManager}, new SecureRandom());
                sslSocketFactory = sc.getSocketFactory();
            } catch (Exception e) {
                LoggerFactory.getLogger("OKHTTP").error("Initial OKHttlClient Error!", e);
            }

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS).connectionPool(new ConnectionPool())
                    .retryOnConnectionFailure(true).sslSocketFactory(sslSocketFactory, trsutManager)
                    .addInterceptor(new GzipRequestInterceptor())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String arg0, SSLSession arg1) {
                            return true;
                        }

                    });
            return builder.build();
        }
    }

    /**
     * TODO:
     *
     * @return
     */
    public static OkHttpClient getHttpClient() {
        return OKHttpClientHelper.HTTPCLIENT_INSTANCE;
    }
}
