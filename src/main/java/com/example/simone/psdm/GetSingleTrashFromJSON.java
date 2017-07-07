package com.example.simone.psdm;

/**
 * Created by Simone on 08/06/2017.
 */

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.simone.psdm.custom_objects.Trash;

/*
    This class implement all the methods needed to transform the InputStream obtained from the server into a Task object
    More info about how the methods work can be found at: https://developer.android.com/reference/android/util/JsonReader.html
 */

public class GetSingleTrashFromJSON {

    public static Trash readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readTrash(reader);
        } finally {
            reader.close();
        }
    }


    public static Trash readTrash(JsonReader reader) throws IOException {
        Trash trash = new Trash();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("trash") && reader.peek() != JsonToken.NULL) {
                trash = readSingleTrash(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return trash;
    }

    public static Trash readSingleTrash(JsonReader reader) throws IOException {
        Trash singleTrash = new Trash();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("type")) {
                singleTrash.setType(reader.nextString());
            } else if (name.equals("typeOfWaste")) {
                singleTrash.setTypeOfWaste(reader.nextString());
            } else if (name.equals("storeName")) {
                singleTrash.setStoreName(reader.nextString());
            } else if (name.equals("id")) {
                singleTrash.setId(reader.nextInt());
            } else if (name.equals("maxVolume")) {
                singleTrash.setMaxVolume(reader.nextDouble());
            } else if (name.equals("currentVolume")) {
                singleTrash.setCurrentVolume(reader.nextDouble());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return singleTrash;
    }

}