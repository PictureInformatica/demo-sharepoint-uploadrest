/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint;

import br.com.picture.demosharepoint.models.ResponseCreateUploadSession;
import br.com.picture.demosharepoint.models.ResponseUploadBytes;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Feign;
import feign.FeignException;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Collections;

public abstract class GraphLargeFileRestUploader {
    private static final Logger logger = LoggerFactory.getLogger(GraphLargeFileRestUploader.class);
    private final String accessToken;
    private final GraphService graph;
    private final String apiUrl;

    GraphLargeFileRestUploader(String accessToken, String apiUrl) {
        this.accessToken = accessToken;
        this.apiUrl = apiUrl;
        graph = Feign.builder()
                .encoder(new JacksonEncoder(Collections.singleton(new JavaTimeModule())))
                .decoder(new JacksonDecoder(Collections.singleton(new JavaTimeModule())))
                .target(GraphService.class, apiUrl);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public GraphService getGraph() {
        return graph;
    }

    public abstract ResponseCreateUploadSession createUploadSession();

    private ResponseUploadBytes uploadBytes(String uploadUrl, byte[] content, long contentLength, String contentRange) throws URISyntaxException {
        GraphService graphBytes = Feign.builder()
                .decoder(new JacksonDecoder(Collections.singleton(new JavaTimeModule())))
                .target(GraphService.class, apiUrl);

        return graphBytes.uploadBytes(new URI(uploadUrl), contentLength, contentRange, content);
    }

    public ResponseUploadBytes uploadLargeFile(Path path) throws URISyntaxException {
        ResponseCreateUploadSession uploadSession = createUploadSession();

        logger.info("Upload session created: {}", uploadSession);

        int rangeStart = 0;
        int rangeEnd;
        ResponseUploadBytes responseUploadBytes = null;
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            FileChannel in = fis.getChannel();
            ByteBuffer fileMap = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
            logger.info("UPLOADING FILE: name='{}'; size='{}; chunk-size={}';", path, in.size(), Constants.FRAGMENT_SIZE);

            fileMap.order(ByteOrder.LITTLE_ENDIAN);

            while (fileMap.hasRemaining()) {
                if(Constants.FRAGMENT_SIZE > 60*1048576) {
                    throw new IllegalStateException("Invalid fragment size, each chunk must be < 60 MiB");
                }

                int length = Constants.FRAGMENT_SIZE;
                if (fileMap.remaining() < Constants.FRAGMENT_SIZE) {
                    length = fileMap.remaining();
                }
                rangeEnd = rangeStart + (length - 1);
                String contentRange = String.format("bytes %d-%d/%d", rangeStart, rangeEnd, in.size());

                byte[] buf = new byte[length];
                fileMap.get(buf);

                logger.info("uploading chunk: length={}; contentRange={}; url={};", length, contentRange, uploadSession.getUploadUrl());

                responseUploadBytes = uploadBytes(uploadSession.getUploadUrl(), buf, length, contentRange);
                logger.info("Uploaded bytes: {}", responseUploadBytes.toString());
                rangeStart = rangeEnd + 1;
            }
        } catch (FeignException | IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            cancelUpload(uploadSession.getUploadUrl());
        }

        return responseUploadBytes;
    }

    public void cancelUpload(String uploadUrl) throws URISyntaxException {
        Response response = graph.cancelUpload(new URI(uploadUrl), getAccessToken());
        if (response.status() == 204) {
            logger.info("Upload cancelled: {}", response);
        }
    }
}
