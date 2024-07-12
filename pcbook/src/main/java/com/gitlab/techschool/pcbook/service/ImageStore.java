package com.gitlab.techschool.pcbook.service;

import java.io.ByteArrayOutputStream;

public interface ImageStore {
    String save (String laptopId, String imageType, ByteArrayOutputStream imageData) throws Exception;
}
