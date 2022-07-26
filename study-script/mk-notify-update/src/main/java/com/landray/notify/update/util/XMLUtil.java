package com.landray.groupid.repair.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author: yaowr
 * @create: 2022-07-14
 */
@Slf4j
public class XMLUtil {

    public static void close(Closeable... closeables) {
        if (closeables != null) {
            for (int i = 0; i < closeables.length; i++) {
                if (closeables[i] != null) {
                    try {
                        closeables[i].close();
                    } catch (IOException e) {
                        log.error("Close failed!", e);
                    }
                }
            }
        }
    }
}
