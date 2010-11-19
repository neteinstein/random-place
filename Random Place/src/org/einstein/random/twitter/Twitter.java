package org.einstein.random.twitter;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import org.apache.http.HttpResponse;
import android.app.Activity;
import android.content.Context;

public class Twitter extends Activity {
	private static final String OAUTH_ADDRESS = "http://api.twitter.com/oauth/";
	private static final String OAUTH_REQUESTTOKEN = "request_token";
	private static final String OAUTH_AUTHORIZE = "authorize";
	private static final String OAUTH_ACCESSTOKEN = "access_token";
	
	private static final String TWITTER_UPDATESTATUS = "http://api.twitter.com/version/statuses/update.XML";
	
	private String consumerKey = null;
	private String consumerSecret = null;
	
	OAuthConsumer consumer = null;
	OAuthProvider provider = null;
	
	public Twitter(String consumerKey, String consumerSecret) {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	
	public String authorize() {
		this.consumer = new DefaultOAuthConsumer(this.consumerKey, this.consumerSecret);

		this.provider = new DefaultOAuthProvider(OAUTH_ADDRESS+OAUTH_REQUESTTOKEN, 
        		OAUTH_ADDRESS+OAUTH_ACCESSTOKEN, OAUTH_ADDRESS+OAUTH_AUTHORIZE);
		
     	try {
			String authUrl = provider.retrieveRequestToken(this.consumer, OAuth.OUT_OF_BAND);
			return authUrl;
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean storeAccessToken(String pin, String fileName) {
		try {
			this.provider.retrieveAccessToken(this.consumer, pin);
			
			FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
			fos.write(new StringBuilder(this.consumer.getToken()).append('\n').append(this.consumer.getTokenSecret()).toString().getBytes());
			fos.close();
			
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean postOnTwitter(String message, String fileName) {
		try {
			// Read tokens from files
			FileInputStream fis = openFileInput(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
			String accessToken = dis.readLine();
			String accessTokenSecret = dis.readLine();
			this.consumer.setTokenWithSecret(accessToken, accessTokenSecret);
			fis.close();
			bis.close();
			dis.close();

			// Prepare connection to Twitter
			HttpPost post = new HttpPost(TWITTER_UPDATESTATUS);
			final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// 'status' here is the update value you collect from UI
			nvps.add(new BasicNameValuePair("status", message));
			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			// set this to avoid 417 error (Expectation Failed)
			post.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			// sign post
			this.consumer.sign(post);
			
			// Send the request
            DefaultHttpClient httpClient = new DefaultHttpClient(getParams());
			final HttpResponse response = httpClient.execute(post);
			// response status should be 200 OK
			int statusCode = response.getStatusLine().getStatusCode();
			final String reason = response.getStatusLine().getReasonPhrase();
			// release connection
			response.getEntity().consumeContent();
			if (statusCode != 200) {
				return false;
			}
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private HttpParams getParams() {
        // Tweak further as needed for your app
        HttpParams params = new BasicHttpParams();
        // set this to false, or else you'll get an
        // Expectation Failed: error
        HttpProtocolParams.setUseExpectContinue(params, false);
        return params;
    }
}
