package hotel;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScannerTypeAdapter extends TypeAdapter<Scanner> {
    @Override
    public void write(JsonWriter out, Scanner value) throws IOException {
        // Skip serialization of the Scanner object by writing null
        out.nullValue();
    }

    @Override
    public Scanner read(JsonReader in) throws IOException {
        // Skip deserialization of the Scanner object by returning null
        return null;
    }
}