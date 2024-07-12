package com.gitlab.techschool.pcbook.service;

import com.pcbook.pb.Filter;
import com.pcbook.pb.Laptop;
import io.grpc.Context;

public interface LaptopStore {
    void save (Laptop laptop) throws Exception;
    Laptop find(String id);
    void Search(Context context, Filter filter, LaptopStream stream);
 }

