/*
 * Copyright (c) 2023 Picture Soluções em TI - All Rights Reserved
 */

package br.com.picture.demosharepoint;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GraphClient {
    private final String clientId;
    private final String clientSecret;
    private final String authority;
    private final String scope;
    private IAuthenticationResult authenticationResult = null;

    public GraphClient(String clientId, String clientSecret, String authority, String scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authority = authority;
        this.scope = scope;
    }

    public String getAccessToken() {
        if (authenticationResult == null || authenticationResult.expiresOnDate().before(new Date())) {
            try {
                requestNewAccessToken();
            } catch (MalformedURLException | InterruptedException | ExecutionException e) {
                throw new AuthenticationException("Authentication failed", e);
            }
        }

        if (authenticationResult == null) {
            throw new AuthenticationException("Authentication failed");
        }
        return authenticationResult.accessToken();
    }

    private void requestNewAccessToken() throws MalformedURLException, ExecutionException, InterruptedException {
        ConfidentialClientApplication clientApplication = ConfidentialClientApplication.builder(clientId,
                        ClientCredentialFactory.createFromSecret(clientSecret))
                .authority(authority)
                .build();

        ClientCredentialParameters clientCredentialParameters = ClientCredentialParameters
                .builder(Collections.singleton(scope))
                .build();

        CompletableFuture<IAuthenticationResult> authenticationResultFuture = clientApplication.acquireToken(clientCredentialParameters);

        authenticationResult = authenticationResultFuture.get();
    }
}
