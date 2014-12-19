package com.popo.tbcake.http.parser.impl;

import com.popo.tbcake.http.parser.Parser;

/**
 * Created by popo on 14-12-19.
 */
public class ParseFactory {


    public static <T> Parser<T> getJsonParser(){
        return new JsonParserImpl<T>();
    }

    public static <T> Parser<T> getXmlParser(){
        return null;
    }
}
