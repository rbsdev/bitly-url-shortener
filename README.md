# A very simple java client for Bitly API

Just shorten urls and caches shortened urls.


#Use

###Get a Bitly access token

Create an application in bitly (https://bitly.com/a/create_oauth_app) and generate an access token.

###Configure the access token in the project for run tests

Paste the access token in the file src/test/resources/bitly.accessToken.

###Compile and build whith Maven

		mvn package

#Code

		URLShortener bitlyShortener = new BitlyURLShortener("bitly access token");
		String shortLink = bitlyShortener.shorten("https://github.com/rbsdev/bitly-url-shortener");

#Reference

[Bitly API Documentation]



[Bitly API Documentation]: http://dev.bitly.com/