package org.einstein.random.twitter;

import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class Twitter extends Activity {

	private String CONSUMER_KEY = "";
	private String CONSUMER_SECRET = "";
	private String CALLBACK_URL = "random://twitter";
	private OAuthProvider provider = null;
	private boolean firstTime = false;

	public void onStart() {
		super.onStart();

		/*if (firstTime) {
			CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(
					CONSUMER_KEY, CONSUMER_SECRET, Signature.HMAC_SHA1);

			provider = new DefaultOAuthProvider(
					"http://twitter.com/oauth/request_token",
					"http://twitter.com/oauth/access_token",
					"http://twitter.com/oauth/authorize");

			HttpClient client = new DefaultHttpClient();

		}*/

	}

	public void onResume() {
		super.onResume();
/*
		// this must be places in activity#onResume()
		Uri uri = this.getIntent().getData();
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(OAUTH_VERIFIER);
			// this will populate token and token_secret in consumer
			provider.retrieveAccessToken(verifier);
		}
	}

	private void getKey(Context context) {
		// Context ctx whichever way you passing it in
		String authUrl = provider.retrieveRequestToken(CALLBACK_URL);
		context
				.startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse(authUrl)));

	}

	public void postTweet(String text) {
		// create a request that requires authentication
		HttpPost post = new HttpPost("http://twitter.com/statuses/update.xml");
		final List<namevaluepair> nvps = new ArrayList<namevaluepair>();
		// 'status' here is the update value you collect from UI
		nvps.add(new BasicNameValuePair("status", status));
		post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		// set this to avoid 417 error (Expectation Failed)
		post.getParams().setBooleanParameter(
				CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		// sign the request
		consumer.sign(post);
		// send the request
		final HttpResponse response = client.execute(post);
		// response status should be 200 OK
		int statusCode = response.getStatusLine().getStatusCode();
		final String reason = response.getStatusLine().getReasonPhrase();
		// release connection
		response.getEntity().consumeContent();
		if (statusCode != 200) {
			Log.e("TwitterConnector", reason);
			throw new OAuthException(reason, e);
		}*/

	}

}
