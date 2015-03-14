package com.rbs.url;

import com.rbs.cache.CacheControl;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Rodrigo Dellinghausen (dellinghausen@gmail.com)
 */
public class BitlyURLShortenerTest {

    private static String BITLY_ACCESS_TOKEN;
    
    @BeforeClass
    public static void setUpClass() {
        InputStream input = BitlyURLShortenerTest.class.getResourceAsStream("/bitly.accessToken");
        Scanner scanner = new Scanner(input);
        BITLY_ACCESS_TOKEN = scanner.nextLine();
    }
    
    @Test
    public void shouldCallBitlyAPIOnce() {
        URLShortener bitlyShortener = new BitlyURLShortener(BITLY_ACCESS_TOKEN);

        String longUrl = "http://zh.clicrbs.com.br/rs/noticias/eleicoes-2014/noticia/2014/10/veja-o-que-rolou-nos-bastidores-do-debate-entre-candidatos-ao-piratini-4610788.html";

        final CacheControl cache = CacheControl.getInstance();

        assertNull(cache.get(longUrl)); 

        String shortLink = bitlyShortener.shorten(longUrl);

        assertNotNull(shortLink);
        assertTrue(shortLink.startsWith("http://"));
        assertNotNull(cache.get(longUrl));

        String shortLink2 = bitlyShortener.shorten(longUrl);

        assertEquals(shortLink, shortLink2);
    }
}
