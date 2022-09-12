package com.umesh.data.test.task;

import com.umesh.data.test.OrderParserApplication;
import com.umesh.data.test.readers.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

/**
 * @author ukushwaha
 */
public class ReaderTask implements Runnable{

    private String fileName;
    private ExecutorService executorService;

    private FileReader fileReader;
    private static final Logger log = LoggerFactory.getLogger(ReaderTask.class);

    public ReaderTask(String fileName, ExecutorService executorService, FileReader fileReader) {
        this.fileName = fileName;
        this.executorService = executorService;
        this.fileReader = fileReader;
    }

    @Override
    public void run() {
        fileReader.read(executorService, fileName);
    }
}
