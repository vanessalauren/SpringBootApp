package Fhir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import Entities.Entity;
import Scrayble.ScraybleController;

import org.apache.log4j.Logger;

public class GaTechProxy {

	private static String http = "http";
	private static String colon = ":";
	private static String slash = "/";
	private static String mainUrl = "polaris.i3l.gatech.edu";
	private static String mainPort = "8080";
	private static String mainApp = "gt-fhir-webapp";
	private static String base = "base";
	private static String jsonFormat = "?_format=json";
	private static Logger log = Logger.getLogger(GaTechProxy.class.getName());
	
	public static String get(String resourceType, String id) {
		log.info("Get Request to GATech FHIR.");
		try {
			StringBuilder sb = new StringBuilder();
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = getURL(resourceType, id);
			log.info("GATech FHIR URL: "+url);
			HttpGet getRequest = new HttpGet(url);
			getRequest.setHeader("Accept", "application/json");
			getRequest.setHeader("Content-Type", "application/json+fhir");
			HttpResponse response = httpClient.execute(getRequest);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			return sb.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			log.error(e.getStackTrace());
		}
		return "";
	}
	
	public static String post(Entity entity) {
		String id = "";
		try {
			String bodyOfPost = entity.getJSONObject().toString();
		    StringEntity se = new StringEntity(bodyOfPost);
		    log.info("Body of Post: " + bodyOfPost);
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = postURL(entity.getResourceType());
		    HttpPost httpPost = new HttpPost(url);
		    httpPost.setEntity(se);
		    httpPost.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(httpPost);
		    id =  response.getHeaders("Location")[0].getValue().replace("http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base/"+entity.getResourceType()+"/", "");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	private static String getURL(String resource, String id) {
	
		StringBuilder sb = new StringBuilder();
		sb.append(http).append(colon).append(slash).append(slash).append(mainUrl).append(colon).append(mainPort).append(slash);
		sb.append(mainApp).append(slash);
		sb.append(base).append(slash);
		sb.append(resource).append(slash).append(id).append(jsonFormat);
		return sb.toString();
		
	}
	
	private static String postURL(String resource) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(http).append(colon).append(slash).append(slash).append(mainUrl).append(colon).append(mainPort).append(slash);
		sb.append(mainApp).append(slash);
		sb.append(base).append(slash);
		sb.append(resource).append(jsonFormat);
		return sb.toString();
		
	}
}
