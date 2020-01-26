package com.fanqi.succulent.util;

import java.io.Closeable;
import java.io.IOException;

public class ClosableCloser {

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
