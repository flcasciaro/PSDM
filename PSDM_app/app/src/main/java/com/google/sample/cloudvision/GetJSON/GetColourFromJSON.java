package com.google.sample.cloudvision.GetJSON;

import android.util.JsonReader;
import android.util.JsonToken;

import com.google.sample.cloudvision.custom_objects.Colour;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Simone on 10/07/2017.
 */

public class GetColourFromJSON {

    public static Colour readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return getColour(reader);
        } finally {
            reader.close();
        }
    }

    public static Colour getColour(JsonReader reader) throws IOException {
        Colour colour = new Colour();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("colour") && reader.peek() != JsonToken.NULL) {
                colour = readColour(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return colour;
    }

    public static Colour readColour(JsonReader reader) throws IOException {
        Colour colour = new Colour();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("code")){
                colour.setCode(reader.nextString());
            } else if (name.equals("wastecontainer")){
                colour.setWasteContainer(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return colour;
    }

}
