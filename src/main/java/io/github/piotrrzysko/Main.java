package io.github.piotrrzysko;

import org.simdjson.JsonValue;
import org.simdjson.SimdJsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IOException {
        byte[] json = loadTwitterJson();

        SimdJsonParser parser = new SimdJsonParser();
        JsonValue jsonValue = parser.parse(json, json.length);
        Iterator<JsonValue> tweets = jsonValue.get("statuses").arrayIterator();
        while (tweets.hasNext()) {
            JsonValue tweet = tweets.next();
            JsonValue user = tweet.get("user");
            if (user.get("default_profile").asBoolean()) {
                System.out.println(user.get("screen_name").asString());
            }
        }
    }

    private static byte[] loadTwitterJson() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/twitter.json")) {
            return is.readAllBytes();
        }
    }
}
