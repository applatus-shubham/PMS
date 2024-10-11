
package com.example.grappler.Validation;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
public class UrlValidator {

    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}

