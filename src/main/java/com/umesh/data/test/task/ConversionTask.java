package com.umesh.data.test.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umesh.data.test.Constant;
import com.umesh.data.test.model.Order;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ukushwaha
 */
public class ConversionTask implements Runnable{

    private Map<String,Object> data;
    private ObjectMapper objectMapper;

    private static AtomicLong counter = new AtomicLong(1);

    public ConversionTask(Map<String,Object> data, ObjectMapper objectMapper){
        this.data = data;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run() {
        data.put(Constant.ID, counter.getAndIncrement());
        if(isValidData()){
            data.put(Constant.RESULT, "OK");
            data.put(Constant.ORDER_ID,convertStringToLong(data.get(Constant.ORDER_ID)));
            data.put(Constant.AMOUNT,convertStringToDouble(data.get(Constant.AMOUNT)));

        }else{
            data.put(Constant.RESULT, "NOT OK");
        }
        try {
            System.out.println(objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Object convertStringToLong(Object number){
        if(number instanceof String){
            return Long.parseLong((String) number);
        }else{
            return number;
        }
    }

    private Object convertStringToDouble(Object number){
        if(number instanceof String){
            return Double.parseDouble((String) number);
        }else{
            return number;
        }
    }
    private boolean isValidData(){
        if(!data.containsKey(Constant.ORDER_ID)
                || !data.containsKey(Constant.AMOUNT)
                || !data.containsKey(Constant.CURRENCY)
                || !data.containsKey(Constant.COMMENT)
                || data.get(Constant.ORDER_ID)== null
                || data.get(Constant.AMOUNT) == null
                || Strings.isEmpty((CharSequence) data.get(Constant.CURRENCY))
                || Strings.isEmpty((CharSequence) data.get(Constant.COMMENT))){

            return false;
        }
        Object amount = data.get(Constant.AMOUNT);
        Object orderId = data.get(Constant.ORDER_ID);
        if(amount instanceof String ){
            try {
                Float.parseFloat((String) amount);
            }catch (NumberFormatException ex){
                return false;
            }
        }
        if(orderId instanceof String){
            try{
                Long.parseLong((String) orderId);
            }catch (NumberFormatException ex){
                return false;
            }
        }
        return true;
    }

}
