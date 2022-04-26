package ru.artem.perfsystem.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    public static List<String> streamToStringList(InputStream inputStream) throws IOException {
        byte[] bytes = inputStream.readAllBytes();
        String fileContentString = new String(bytes);
        String[] splitContentArray = fileContentString.split("\n");
        return new ArrayList<>(Arrays.asList(splitContentArray));
    }

}
