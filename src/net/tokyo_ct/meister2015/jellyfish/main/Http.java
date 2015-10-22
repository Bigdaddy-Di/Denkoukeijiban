package net.tokyo_ct.meister2015.jellyfish.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class Http {
	String url = "";
	RequestConfig rc;
	HttpClient hc;

	public Http(String url) {
		this.url = url;
		rc = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();

		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Accept-Charset", "UTF-8"));
		headers.add(new BasicHeader("User-Agent", "Denkoukeijiban"));

		hc = HttpClientBuilder.create().setDefaultRequestConfig(rc).setDefaultHeaders(headers).build();
	}

	public String getToUrl() {
		String body = "";
		try {
			HttpGet hg = new HttpGet(url);
			HttpResponse hr = hc.execute(hg);
			// int status = hr.getStatusLine().getStatusCode();
			body = EntityUtils.toString(hr.getEntity(), "UTF-8");
			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return body;

	}
}
