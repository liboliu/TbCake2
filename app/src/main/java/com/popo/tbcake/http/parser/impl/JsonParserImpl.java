package com.popo.tbcake.http.parser.impl;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.popo.tbcake.http.parser.Parser;
import com.google.gson.Gson;

public class JsonParserImpl<T> extends BaseParser<T> {
    Gson g = new Gson();

    @Override
    public T parse(String s, Class<T> t) throws Exception {
        // TODO Auto-generated method stub

        return g.fromJson(s, t);
    }

    @Override
    public T parse(InputStream in, Class<T> t) throws Exception {
        // TODO Auto-generated method stub
        return g.fromJson(new InputStreamReader(in), t);
    }


}
