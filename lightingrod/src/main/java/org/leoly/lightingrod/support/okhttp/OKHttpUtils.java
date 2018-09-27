/**
 * copywrite 2015-2020 智慧享联
 * 不能修改和删除上面的版权声明
 * 此代码属于智慧享联编写，在未经允许的情况下不得传播复制
 * OKHttpUtils.java
 * @Date 2018年5月4日 上午9:33:47
 * guguihe
 */
package org.leoly.lightingrod.support.okhttp;

import okhttp3.*;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.leoly.lightingrod.support.gson.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * TODO:
 * 
 * @author guguihe
 * @Date 2018年5月4日 上午9:33:47
 */
public class OKHttpUtils {
	private final Logger logger = LoggerFactory.getLogger("OkHttpService");

	private OkHttpClient httpClient = null;

	private OKHttpUtils() {
		httpClient = OKHttpClientFactory.getHttpClient();
	}

	private static class OKHttpHelper {
		private static final OKHttpUtils OKHTTP_UTILS_INSTANCE = new OKHttpUtils();
	}

	public static OKHttpUtils getHttpUtils() {
		return OKHttpHelper.OKHTTP_UTILS_INSTANCE;
	}

	/**
	 * 默认的回调实现
	 */
	private final Callback defaultCallback = new Callback() {
		@Override
		public void onResponse(Call call, Response response) throws IOException {
			if (response.isSuccessful()) {
				String responstStr = IOUtils.toString(response.body().charStream());
				logger.info("OkHttp Message: " + response.message() + ", Response String: " + responstStr);
			} else {
				logger.error("OkHttp Message: " + response.message() + ", Response Code: " + response.code());
			}
		}

		@Override
		public void onFailure(Call call, IOException e) {
			logger.error("OkHttp Request Error, URL: " + call.request().url().toString(), e);
		}
	};

	/**
	 * @return the defaultCallback
	 */
	public Callback getDefaultCallback() {
		return defaultCallback;
	}

	private String proxyHost;

	private String proxyPort;

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public String httpGet(String url) throws OKHttpRequestException {
		return httpGet(url, String.class);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public String httpPost(String url, String jsonContent) throws OKHttpRequestException {
		return httpPost(url, jsonContent, String.class);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public <T> T httpPost(String url, RequestBody requestBody, Class<T> tc) throws OKHttpRequestException {
		logger.info("OkHttp Request URL: " + url);
		try {
			Request request = new Request.Builder().url(url).post(requestBody).build();
			Call call = httpClient.newCall(request);
			Response response = call.execute();
			return response(response, tc);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public <T> T httpPost(String url, String jsonContent, Class<T> tc) throws OKHttpRequestException {
		try {
			RequestBody body = RequestBody.create(MediaType.parse("application/*; charset=utf-8"), jsonContent);
			return httpPost(url, body, tc);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 处理请求结果
	 * 
	 * @param response
	 * @throws IOException
	 */
	private <T> T response(Response response, Class<T> tc) throws OKHttpRequestException, IOException {
		if (response.isSuccessful()) {
			String responstStr = IOUtils.toString(response.body().charStream());
			logger.info("OkHttp Message: " + response.message() + ", Response String: " + responstStr);
			return GsonUtils.toObject(responstStr, tc);
		} else {
			throw new OKHttpRequestException("Unexpected response code: " + response.code());
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public <T> T httpGet(String url, Class<T> tc) throws OKHttpRequestException {
		logger.info("OkHttp Request URL: " + url);
		try {
			Request request = new Request.Builder().url(url).build();
			Call call = httpClient.newCall(request);
			Response response = call.execute();
			return response(response, tc);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncGet(String url, Callback callback) throws OKHttpRequestException {
		try {
			Request request = new Request.Builder().url(url).build();
			Call call = httpClient.newCall(request);
			call.enqueue(callback);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncGet(String url) throws OKHttpRequestException {
		asyncGet(url, defaultCallback);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncPost(String url, String jsonContent) throws OKHttpRequestException {
		asyncPost(url, jsonContent, defaultCallback);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncPost(String url, String jsonContent, Callback callback) throws OKHttpRequestException {
		RequestBody body = RequestBody.create(MediaType.parse("application/*; charset=utf-8"), jsonContent);
		asyncPost(url, body, callback);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncPost(String url, RequestBody requestBody, Callback callback) throws OKHttpRequestException {
		try {
			Request request = new Request.Builder().url(url).post(requestBody).build();
			Call call = httpClient.newCall(request);
			call.enqueue(callback);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncProxyGet(String url, Callback callback) throws OKHttpRequestException {
		try {
			Request request = new Request.Builder().url(url).build();
			Call call = httpClient.newBuilder().proxy(getProxy()).build().newCall(request);
			call.enqueue(callback);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncProxyGet(String url) throws OKHttpRequestException {
		asyncProxyGet(url, defaultCallback);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncProxyPost(String url, String jsonContent) throws OKHttpRequestException {
		asyncProxyPost(url, jsonContent, defaultCallback);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncProxyPost(String url, RequestBody requestBody, Callback callback) throws OKHttpRequestException {
		try {
			Request request = new Request.Builder().url(url).post(requestBody).build();
			Call call = httpClient.newBuilder().proxy(getProxy()).build().newCall(request);
			call.enqueue(callback);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public void asyncProxyPost(String url, String jsonContent, Callback callback) throws OKHttpRequestException {
		try {
			RequestBody body = RequestBody.create(MediaType.parse("application/*; charset=utf-8"), jsonContent);
			asyncProxyPost(url, body, callback);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public String proxyPost(String url, String jsonContent) throws OKHttpRequestException {
		return proxyPost(url, jsonContent, String.class);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public <T> T proxyPost(String url, RequestBody requestBody, Class<T> tc) throws OKHttpRequestException {
		try {
			Request request = new Request.Builder().url(url).post(requestBody).build();
			Proxy proxy = getProxy();
			Call call = httpClient.newBuilder().proxy(proxy).build().newCall(request);
			Response response = call.execute();
			return response(response, tc);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public <T> T proxyPost(String url, String jsonContent, Class<T> tc) throws OKHttpRequestException {
		try {
			RequestBody body = RequestBody.create(MediaType.parse("application/*; charset=utf-8"), jsonContent);
			return proxyPost(url, body, tc);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public String proxyGet(String url) throws OKHttpRequestException {
		return proxyGet(url, String.class);
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public InputStream getInputStream(String url) throws OKHttpRequestException {
		logger.info("OkHttp Request URL: " + url);
		try {
			Request request = new Request.Builder().url(url).build();
			Call call = httpClient.newBuilder().build().newCall(request);
			Response response = call.execute();
			return response.body().byteStream();
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 使用OkHttp发起Get请求
	 * 
	 * @param url
	 * @param tc
	 * @return
	 * @throws OKHttpRequestException
	 */
	public <T> T proxyGet(String url, Class<T> tc) throws OKHttpRequestException {
		logger.info("OkHttp Request URL: " + url);
		try {
			Request request = new Request.Builder().url(url).build();
			Proxy proxy = getProxy();
			Call call = httpClient.newBuilder().proxy(proxy).build().newCall(request);
			Response response = call.execute();
			return response(response, tc);
		} catch (Exception e) {
			logger.error("OkHttp请求发生异常！", e);
			throw new OKHttpRequestException(e.getMessage(), e);
		}
	}

	/**
	 * TODO: 获取代理信息
	 * 
	 * @return
	 * @throws OKHttpRequestException
	 */
	private Proxy getProxy() throws OKHttpRequestException {
		if (StringUtils.isEmpty(this.proxyHost)) {
			throw new OKHttpRequestException("未配置代理服务器信息：http.proxy.host！");
		}

		if (StringUtils.isEmpty(this.proxyPort) || !StringUtils.isNumeric(this.proxyPort)) {
			throw new OKHttpRequestException("未配置代理服务器信息：http.proxy.port！");
		}

		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.proxyHost, Integer.valueOf(this.proxyPort)));
	}

	public OKHttpUtils setProxy(String proxyHost, String proxyPort){
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		return this;
	}
}
