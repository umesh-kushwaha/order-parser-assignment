package com.umesh.data.test.readers;

import java.util.concurrent.ExecutorService;

/**
 * @author ukushwaha
 */
public interface FileReader {
    void read(ExecutorService executorService, String filePath);
}
