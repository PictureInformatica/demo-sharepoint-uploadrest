/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint;

import br.com.picture.demosharepoint.models.RequestCreateUploadSession;
import br.com.picture.demosharepoint.models.ResponseCreateUploadSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class SharepointRestUploader extends GraphLargeFileRestUploader {
    private static final Logger logger = LoggerFactory.getLogger(SharepointRestUploader.class);
    private final String siteName;
    private final String destinationPath;
    private final Path sourceFile;

    public SharepointRestUploader(String accessToken, String siteName, String destinationPath, Path sourceFile) {
        super(accessToken, Application.settings.getApiUrl());
        this.siteName = siteName;
        this.destinationPath = destinationPath;
        this.sourceFile = sourceFile;
    }

    public ResponseCreateUploadSession createUploadSession() {
        logger.info("Creating Upload Session (siteName={}; sourceFile={}; destinationPath={})", siteName, sourceFile, destinationPath);
        RequestCreateUploadSession requestCreateUploadSession = new RequestCreateUploadSession();
        requestCreateUploadSession.setName(sourceFile.getFileName().toString());
        requestCreateUploadSession.setConflictBehavior("replace");
        return getGraph().createUploadSession(getAccessToken(), siteName, URLEncoder.encode(destinationPath, StandardCharsets.UTF_8), requestCreateUploadSession);
    }
}
