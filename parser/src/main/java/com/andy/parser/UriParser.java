package com.andy.parser;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

/**
 * @author wuhaibo
 * create date: 2018/5/30.
 */
public class UriParser {

    public static Bundle parse(@NonNull Uri uri) {
        String url = uri.toString();
        if (url.isEmpty()) {
            return null;
        }

        return parse(url);
    }

    @Nullable
    public static Bundle parse(@NonNull String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.isEmpty()) {
            return null;
        }


        Uri uri = null;
        try {
            uri = Uri.parse(url);
        } catch (Exception e) {
            // ignored
        }

        if (uri == null) {
            return null;
        }

        Set<String> keySet = uri.getQueryParameterNames();
        if (keySet == null || keySet.isEmpty()) {
            return null;
        }

        Bundle bundle = new Bundle();
        for (String key : keySet) {
            String valStr = uri.getQueryParameter(key);
            if (valStr == null || valStr.isEmpty()) {
                continue;
            }
            if (TypeMatcher.isByte(valStr)) {
                Byte valByte = StringParser.parseByte(valStr);
                if (valByte != null) {

                }
            } else if(TypeMatcher.isShort(valStr)) {
                Short valShort = StringParser.parseShort(valStr);
            }
        }

        return bundle;
    }

}
