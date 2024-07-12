package com.gitlab.techschool.pcbook.service;

import com.pcbook.pb.Filter;
import com.pcbook.pb.Laptop;
import com.pcbook.pb.Memory;
import io.grpc.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class InMemoryLaptopStore implements LaptopStore{

    private static final Logger logger = Logger.getLogger(InMemoryLaptopStore.class.getName());

    private ConcurrentMap<String, Laptop> data;

    public InMemoryLaptopStore(){
        data = new ConcurrentHashMap<>(0);
    }

    @Override
    public void save(Laptop laptop) throws Exception {
        if (data.containsKey(laptop.getId())){
            throw new AlreadyExistsException ("laptop ID already exists");
        }

        Laptop other = laptop.toBuilder().build();
        data.put(other.getId(), other);
    }

    @Override
    public Laptop find(String id) {
        if (!data.containsKey(id))
            return null;

        return data.get(id).toBuilder().build();
    }

    @Override
    public void Search(Context context, Filter filter, LaptopStream stream) {
        for (Map.Entry<String, Laptop> entry: data.entrySet()) {
            if (context.isCancelled()){
                logger.info("context is cancelled");
                return;
            }
//            Timeout
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Laptop laptop = entry.getValue();
            if (isQualified(filter, laptop)) {
                stream.send(laptop.toBuilder().build());
            }
        }
    }

    private boolean isQualified(Filter filter, Laptop laptop) {
        if (laptop.getPrizeUsd() > filter.getMaxPriceUsd()) {
            return false;
        }

        if (laptop.getCpu().getNumberCores() < filter.getMinCpuCores()) {
            return false;
        }

        if (laptop.getCpu().getMinGhz() < filter.getMinCpuGhz()) {
            return false;
        }

        if (toBit(laptop.getRam()) < toBit(filter.getMinRam())) {
            return false;
        }
        return true;
    }

    private long toBit(Memory memory) {
        long value = memory.getValue();

        return switch (memory.getUnit()) {
            case BIT -> value;
            case BYTE -> value << 3;
            case KILOBYTE -> value << 13;
            case MEGABYTE -> value << 23;
            case GIGABYTE -> value << 33;
            case TERABYTE -> value << 43;
            default -> 0;
        };
    }
}
