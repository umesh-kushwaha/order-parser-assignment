package com.umesh.data.test.readers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umesh.data.test.model.FileType;
import org.apache.commons.io.FilenameUtils;

/**
 * @author ukushwaha
 */
public class FileReaderFactory {

    public static FileReader getFileReader(String filename){
        String extention = FilenameUtils.getExtension(filename);
        if(FileType.CSV.name().equalsIgnoreCase(extention)){
            return new CSVFileReader(filename);
        }else if(FileType.JSON.name().equalsIgnoreCase(extention)){
            return new JsonFileReader(filename, new ObjectMapper());
        }else{
            throw new RuntimeException("File format does not supported");
        }
    }
}
