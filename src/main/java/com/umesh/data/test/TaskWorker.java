package com.umesh.data.test;

import com.umesh.data.test.readers.FileReaderFactory;
import com.umesh.data.test.task.ReaderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author ukushwaha
 */
@Component
public class TaskWorker {


    @Autowired
    private ExecutorService fixedThreadPoolExecutor;

    public void parseOrderFiles(List<String> fileNameList){
        fileNameList.forEach(fileName ->
                fixedThreadPoolExecutor.submit(new ReaderTask(fileName,
                        fixedThreadPoolExecutor, FileReaderFactory.getFileReader(fileName))));

    }

}
