package com.umesh.data.test.readers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umesh.data.test.Constant;
import com.umesh.data.test.task.ConversionTask;
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
public class JsonFileReader implements FileReader{

    private String fileName;
    private ObjectMapper objectMapper;
    private long counter = 1;

    private static final Logger log = LoggerFactory.getLogger(JsonFileReader.class);

    public JsonFileReader(String fileName, ObjectMapper objectMapper) {
        this.fileName = fileName;
        this.objectMapper = objectMapper;
    }

    @Override
    public void read(ExecutorService executorService, String fileName) {
        //read file into stream, try-with-resources

        String filePath = Constant.FILE_BASE_PATH + fileName;
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            // stream.forEach(System.out::println);
            stream.forEach(line -> {
                Map<String,Object> data = new HashMap<>();
                try {
                    data = objectMapper.readValue(line, Map.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                data.put(Constant.LINE_NO, counter);
                data.put(Constant.FILENAME, fileName);

                counter++;
                executorService.submit(new ConversionTask(data, objectMapper));
            });
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Failed to read file {} with error {}", filePath, e.getMessage());
        }
    }
}
