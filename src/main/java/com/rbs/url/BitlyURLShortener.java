package com.rbs.url;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import com.rbs.cache.CacheControl;

/**
 * 
 * @author Rodrigo Dellinghausen (dellinghausen@gmail.com)
 */
public class BitlyURLShortener implements URLShortener {

    private static final Logger log = Logger.getLogger(BitlyURLShortener.class.getName());

    private static final String BITLY_SERVICE_URL = "https://api-ssl.bitly.com/v3/shorten"; 

    private final String serviceUrl;
    private final String accessToken;

    public BitlyURLShortener(String accessToken) {
        this(BITLY_SERVICE_URL, accessToken);
    }

    public BitlyURLShortener(String serviceUrl, String accessToken) {
        this.serviceUrl = serviceUrl;
        this.accessToken = accessToken;
    }

    public String shorten(String url) {

        final CacheControl cache = CacheControl.getInstance();

        String shorten = cache.get(url);

        if (shorten == null) {
            shorten = getShortUrlFromBitly(url);
            if (shorten != null) {
                cache.put(url, shorten);
            }
        }

        return shorten;
    }

    private String getShortUrlFromBitly(String url) {
        String shorten = null;
        try {
            log.log(Level.WARNING, "Calling Bitly service...");
            final Response response = 
                    Request.Post(serviceUrl)
                            .bodyForm(
                                    Form.form()
                                            .add("format", "json")
                                            .add("access_token", accessToken)
                                            .add("longUrl", url)
                                            .build()).execute();

            Content content = response.returnContent();

            shorten = getShortenURLFromJSONStringContent(content.asString());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: ", e);
        }

        return shorten;
    }

    private String getShortenURLFromJSONStringContent(String jsonContent) {
        JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonContent);

        String shorten = null;
        if (json.has("data")) {
            JSONObject jsonData = json.getJSONObject("data");
            if (jsonData.has("url")) {
                shorten = jsonData.getString("url");
            }
        }

        if ( shorten == null) {
            throw new IllegalStateException("Invalid data format: "+ jsonContent);
        }

        return shorten;
    }
}