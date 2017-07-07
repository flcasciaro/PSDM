package com.example.simone.psdm;

/**
 * Created by Simone on 05/06/2017.
 */

        import android.util.JsonReader;
        import android.util.JsonToken;

        import com.example.simone.psdm.custom_objects.Trash;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.List;

public class GetTrashListFromJSON {

    public static List<Trash> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readTrashArray(reader);
        } finally {
            reader.close();
        }
    }

    public static List<Trash> readTrashArray(JsonReader reader) throws IOException {
        List<Trash> trash = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("trash") && reader.peek() != JsonToken.NULL) {

                trash = getTrash(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return trash;
    }

    public static List<Trash> getTrash(JsonReader reader) throws IOException
    {
        List<Trash> trash = new ArrayList<Trash>();

        reader.beginArray();
        while (reader.hasNext()) {
            trash.add(readSingleTrash(reader));
        }
        reader.endArray();
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
