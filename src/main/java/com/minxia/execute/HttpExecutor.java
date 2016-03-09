package com.minxia.execute;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import com.minxia.log.Log;
import com.minxia.model.HttpForm;

public class HttpExecutor implements Executor{
	private HttpForm form;
	static int BASE_BODY_SIZE = 10240;
	static int INC_BODY_SIZE = 51200;
	
	public HttpExecutor(HttpForm form){
		this.form = form;
	}

	@Override
	public void Execute() {
		String result = null;
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(this.form.getUrl());
		String input = this.form.getInput();
		Log.out("http input: " + input);
		URLEncoder.encode(input);
		InputStream data = new ByteArrayInputStream(input.getBytes());
		method.setRequestBody(data);

		try{
			int retcode = httpClient.executeMethod(method);
			if (retcode == HttpStatus.SC_OK)
			{
				byte[] responseBody = new byte[BASE_BODY_SIZE];

				java.io.InputStream istream = method.getResponseBodyAsStream();
				int npos = 0;
				int nread = 0;
				while ((nread = istream.read(responseBody, npos, responseBody.length - npos)) >= 0)
				{
					npos += nread;
					if (npos >= responseBody.length)
					{
						byte[] tmpBuf = new byte[npos + INC_BODY_SIZE];
						System.arraycopy(responseBody, 0, tmpBuf, 0, npos);
						responseBody = tmpBuf;
					}
				}

				// byte[] responseBody = method.getResponseBody();
				result = new String(responseBody, 0, npos);
			}
			else
			{
				result = "failed to send request: retcode: " + retcode;
			}
		}catch(IOException e){
			result = "send request error" + e.getMessage();
		}finally{
			// always release the connection after the request is done
			method.releaseConnection();
			if (data != null)
			{
				try
				{
					data.close();
				}
				catch (Exception ex)
				{

				}
			}
		}
		Log.out("http result:" + result);
		this.form.setOutput(result);
	}

	private void sendPost() throws Exception {
		String url_link = "http://192.168.254.198:3020/service/unify";
//		String url_link = "http://localhost:3020/service/unify";
		URL url = new URL(url_link);
		URLConnection urlcon = url.openConnection();
		HttpURLConnection con = (HttpURLConnection) urlcon;
		
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <request> <header> <timestamp>1234567890</timestamp> <userid>nxiao</userid> <session>abcd-ddd-222</session> <reqid>req112334455</reqid> <ip>192.168.254.111</ip> <timeout>60</timeout> </header> <input format=\"ttl\"> <![CDATA[@prefix xsd: <http://www.w3.org/2001/XMLSchema#> . @prefix owl: <http://www.w3.org/2002/07/owl#> . @prefix xml: <http://www.w3.org/XML/1998/namespace> . @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> . @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> . @prefix rule: <http://www.cyberobject.com/2012/12/rule#> . @prefix term: <http://www.cyberobject.com/2012/12/term#> . @prefix ntelagent: <http://www.cyberobject.com/2012/12/cyberobject/ntelagent#> . @prefix telecom: <http://www.cyberobject.com/2012/12/domain/telecom#> . @prefix cyberobject: <http://www.cyberobject.com/2012/12/organization/cyberobject#> . @prefix iplatform: <http://www.cyberobject.com/2012/12/iplatform#> . @prefix text: <http://www.cyberobject.com/2012/12/cyberobject/ntelagent/text#> . @prefix nn: <http://www.cyberobject.com/2012/12/cyberobject/ntelagent/nn#> . @prefix zd585d2e7: <http://www.cyberobject.com/2012/12/cyberobject/ntelagent/zd585d2e7#> . @prefix zbfe7463b: <http://www.cyberobject.com/2012/12/cyberobject/ntelagent/zbfe7463b#> . ntelagent:zd585d2e7 rule:unify ntelagent:zbfe7463b . ntelagent:zd585d2e7 {zd585d2e7:what-1x1 rdf:type rule:var ; rule:lemma \"what\" ; rdf:type term:TERM . zd585d2e7:today-1x3 rule:hasValue \"THIS-P1D\" ; rdf:type term:DATETIME ; term:has zd585d2e7:weather-1x5 ; rule:lemma \"today\" ; rule:sense \"THIS-P1D\" . zd585d2e7:weather-1x5 rdf:type term:WEATHER ; rdf:type rule:WHSQ ; rule:lemma \"weather\" ; rule:sense \"11524662\" ; rule:hasATTR zd585d2e7:what-1x1 ; rule:isHeadword \"true\" . } ntelagent:zbfe7463b {zbfe7463b:today-1x1 rule:hasValue \"THIS-P1D\" ; rdf:type term:DATETIME ; term:has zbfe7463b:weather-1x3 ; rule:lemma \"today\" ; rule:sense \"THIS-P1D\" . zbfe7463b:weather-1x3 rdf:type term:WEATHER ; rule:lemma \"weather\" ; rule:sense \"11524662\" ; term:has zbfe7463b:cloudy-1x5 . zbfe7463b:cloudy-1x5 rdf:type term:OPACITY ; rule:lemma \"cloudy\" ; rule:sense \"781974\" ; rule:isHeadword \"true\" . } ]]> </input> </request>";
		String urlParameters = URLEncoder.encode(input);
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		Log.out("\nSending 'POST' request to URL : " + url);
		Log.out("Post parameters : " + urlParameters);
		Log.out("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			response.append("\n");
		}
		in.close();
 
		//print result
		Log.out(response.toString());
 
	}
	private final String USER_AGENT = "Mozilla/5.0";
}
