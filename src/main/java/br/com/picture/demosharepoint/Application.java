/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint;

import br.com.picture.demosharepoint.models.ResponseUploadBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static Settings settings = new Settings();

    public static void main(String[] args) throws IOException, URISyntaxException {
        Path path = null;
        if (args != null && args.length == 1) {
            path = Paths.get(args[0]);
        } else {
            logger.error("Usage:\n\tjava -jar application.jar file-to-upload");
            System.exit(1);
        }

        String destination = Constants.REMOTE_FOLDER + path.getFileName();

        GraphClient graphClient = new GraphClient(settings.getClientId(),
                settings.getClientSecret(),
                String.format("%s/%s", settings.getOauthUrl(), settings.getTenant()),
                settings.getOauthScope()
        );

        SharepointRestUploader sharepointUploader = new SharepointRestUploader(
                graphClient.getAccessToken(),
                settings.getSharepointSite(),
                destination,
                path
        );

        ResponseUploadBytes responseUploadBytes = sharepointUploader.uploadLargeFile(path);

        if (responseUploadBytes != null && responseUploadBytes.getId() != null && responseUploadBytes.getName() != null) {
            logger.info("File uploaded: name: {}; id: {}; mimeType: {}; size: {}; hashes: {}",
                    responseUploadBytes.getName(),
                    responseUploadBytes.getId(),
                    responseUploadBytes.getFile() != null ? responseUploadBytes.getFile().getMimeType() : "",
                    responseUploadBytes.getSize(),
                    responseUploadBytes.getFile() != null ? responseUploadBytes.getFile().getHashes() : ""
            );
        }
    }
}
