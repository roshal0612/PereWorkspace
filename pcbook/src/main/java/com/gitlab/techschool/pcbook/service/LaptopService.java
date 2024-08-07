package com.gitlab.techschool.pcbook.service;

import com.google.protobuf.ByteString;
import com.pcbook.pb.*;
import com.pcbook.pb.LaptopServiceGrpc.LaptopServiceImplBase;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

public class LaptopService extends LaptopServiceImplBase {
    private static final Logger logger = Logger.getLogger(LaptopService.class.getName());

    private LaptopStore laptopStore;
    private ImageStore imageStore;

    public LaptopService (LaptopStore laptopStore, ImageStore imageStore){
        this.laptopStore = laptopStore;
        this.imageStore = imageStore;
    }

    @Override
    public void createLaptop(CreateLaptopRequest request, StreamObserver<com.pcbook.pb.CreateLaptopResponse> responseObserver) {
        Laptop laptop = request.getLaptop();
        String id = laptop.getId();

        logger.info("got a create-laptop request with ID: " + id);

        UUID uuid;
        if (id.isEmpty()) {
            uuid = UUID.randomUUID();
        } else {
            try {
                uuid = UUID.fromString(id);
            } catch (IllegalArgumentException e){
                responseObserver.onError(
                        Status.INVALID_ARGUMENT
                                .withDescription(e.getMessage())
                                .asRuntimeException()
                );
                return;
            }
        }

        // heavy processing
//        try {
//            TimeUnit.SECONDS.sleep(6);
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        }

        if (Context.current().isCancelled()){
            logger.info("request is cancelled");
            responseObserver.onError(
                    Status.CANCELLED
                            .withDescription("resquest is cancelled")
                            .asRuntimeException()
            );
            return;
        }

        Laptop other = laptop.toBuilder().setId(uuid.toString()).build();
        try {
            laptopStore.save(other);
        } catch (AlreadyExistsException e){
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        } catch (Exception e){
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }

        CreateLaptopResponse response = CreateLaptopResponse.newBuilder().setId(other.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        logger.info("saved laptop with ID :" + other.getId());
    }

    @Override
    public void searchLaptop(SearchLaptopRequest request, StreamObserver<SearchLaptopResponse> responseObserver) {
        Filter filter = request.getFilter();
        logger.info("got a search-laptop request with filter:\n" + filter);

        laptopStore.Search(Context.current(), filter, new LaptopStream() {
            @Override
            public void send(Laptop laptop) {
                logger.info("found laptop with Id: " + laptop.getId());
                SearchLaptopResponse response = SearchLaptopResponse.newBuilder().setLaptop(laptop).build();
                responseObserver.onNext(response);
            }
        });

        responseObserver.onCompleted();
        logger.info("search completed");
    }

    @Override
    public StreamObserver<UploadImageRequest> uploadImage(StreamObserver<UploadImageResponse> responseObserver) {
        return new StreamObserver<UploadImageRequest>() {
            private static final int maxImageSize = 1 << 20; //1 megabyte
            private String laptopID;
            private String imageType;
            private ByteArrayOutputStream imageData;

            @Override
            public void onNext(UploadImageRequest request) {
                if (request.getDataCase() == UploadImageRequest.DataCase.INFO) {
                    ImageInfo info = request.getInfo();
                    logger.info("receive image info:\n" + info);

                    laptopID = info.getLaptopId();
                    imageType = info.getImageType();
                    imageData = new ByteArrayOutputStream();

                    Laptop found = laptopStore.find(laptopID);
                    if (found == null) {
                        responseObserver.onError(
                                Status.NOT_FOUND
                                        .withDescription("laptop ID doesn't exist")
                                        .asRuntimeException()
                        );
                    }
                    return;
                }
                ByteString chunkData = request.getChunkData();
                logger.info("receive image chunk with size: " + chunkData.size());

                if (imageData == null){
                    logger.info("image info wasn't sent before");
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("image info wasn't sent before")
                                    .asRuntimeException()
                    );
                    return;
                }

                int size = imageData.size() + chunkData.size();
                if (size > maxImageSize){
                    logger.info("image is too large: " + size);
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("image is too large: " + size)
                                    .asRuntimeException()
                    );
                    return;
                }

                try {
                    chunkData.writeTo(imageData);
                } catch (IOException e) {
                    responseObserver.onError(
                            Status.INTERNAL
                                    .withDescription("cannot write chunk data: " + e.getMessage())
                                    .asRuntimeException()
                    );
                    return;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.warning(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                String imageID = "";
                int imageSize = imageData.size();
                try {
                    imageID = imageStore.save(laptopID, imageType, imageData);
                } catch (Exception e) {
                    responseObserver.onError(
                            Status.INTERNAL
                                    .withDescription("cannot save image to the store: " + e.getMessage())
                                    .asRuntimeException()
                    );
                    return;
                }

                UploadImageResponse response = UploadImageResponse.newBuilder()
                        .setId(imageID)
                        .setSize(imageSize)
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }

        };
    }
}
