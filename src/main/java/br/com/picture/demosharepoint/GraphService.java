/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint;

import br.com.picture.demosharepoint.models.RequestCreateUploadSession;
import br.com.picture.demosharepoint.models.ResponseCreateUploadSession;
import br.com.picture.demosharepoint.models.ResponseUploadBytes;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.net.URI;

public interface GraphService {
    @RequestLine(value = "POST /sites/{siteId}/drive/root:/{itemPath}:/createUploadSession", decodeSlash = false)
    @Headers({"Authorization: Bearer {accessToken}","Content-Type: application/json"})
    ResponseCreateUploadSession createUploadSession(@Param("accessToken") String accessToken,
                                                    @Param("siteId") String siteId,
                                                    @Param("itemPath") String itemPath,
                                                    RequestCreateUploadSession createUploadSession);

    @RequestLine("PUT")
    @Headers({"Content-Length: {contentLength}", "Content-Range: {contentRange}","Content-Type: application/octet-stream"})
    ResponseUploadBytes uploadBytes(URI uploadUrl,
                                    @Param("contentLength") long contentLength,
                                    @Param("contentRange") String contentRange,
                                    byte[] content);

    @RequestLine("DELETE")
    @Headers("Authorization: Bearer {accessToken}")
    Response     cancelUpload(URI uploadUrl, @Param("accessToken") String accessToken);
}
