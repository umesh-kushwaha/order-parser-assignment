package com.umesh.data.test.readers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umesh.data.test.Constant;
import com.umesh.data.test.task.ConversionTask;
import com.umesh.data.test.task.ReaderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

/**
 * @author ukushwaha
 */
public class CSVFileReader implements FileReader{

    private static final Logger log = LoggerFactory.getLogger(CSVFileReader.class);

    private String fileName;
    private long counter = 1;

    public CSVFileReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void read(ExecutorService executorService, String fileName) {

        String filePath = Constant.FILE_BASE_PATH + fileName;

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                Map<String,Object> data = new HashMap<>();
                String[] fieldValue = line.split(",");
                data.put(Constant.ORDER_ID, fieldValue[0]);
                data.put(Constant.AMOUNT, fieldValue[1]);
                data.put(Constant.CURRENCY, fieldValue[2]);
                data.put(Constant.COMMENT, fieldValue[3]);
                data.put(Constant.LINE_NO,counter );
                data.put(Constant.FILENAME, fileName);
                counter++;
                executorService.submit(new ConversionTask(data, new ObjectMapper()));
            });
        } catch (IOException e) {
            log.error("Failed to read file {} with error {}", filePath, e.getMessage());
        }
    }
}
