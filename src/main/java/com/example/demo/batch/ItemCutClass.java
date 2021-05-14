package com.example.demo.batch;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class ItemCutClass implements Partitioner {
    String []category = {"food", "clothes", "beauty", "pet", "sport", "home", "car", "travel", "digital", "book"};
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        for(int i =0 ; i< gridSize; i++){
            ExecutionContext value = new ExecutionContext();
            value.putInt("index", i);
            value.putInt("gridSize", gridSize);
            value.putString("category", category[i]);
            result.put("partition"+(i+1), value);

        }

        return result;
    }
}
