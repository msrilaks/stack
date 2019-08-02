package com.stack.email.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Profile;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GmailService {
    private static String                      STACK_EMAIL = "stackitdown@gmail.com";
    private        DataStore<StoredCredential> dataStore;
    private        Gmail                       gmail;

    @Autowired
    public GmailService(
            @Value("${credential.file}") String credentialFile,
            @Value("${credential.dir}") String credentialDir) {
        String APPLICATION_NAME = "STACK_GMAIL";
        try {
            //            dataStore = MemoryDataStoreFactory.getDefaultInstance()
            //            .getDataStore(
            //                    "credentialDatastore");
            FileDataStoreFactory dataStoreFactory =
                    new FileDataStoreFactory(new File(credentialDir));
            dataStore = dataStoreFactory.getDataStore(credentialFile);

        } catch (IOException e) {
            throw new RuntimeException("Unable to create in memory credential datastore",
                                       e);
        }
        try {

            // Set up the HTTP transport and JSON factory
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            // Load client secrets

            URL secretUrl = Resources.getResource("client_secrets_develop.json");
            CharSource inputSupplier =
                    Resources.asCharSource(secretUrl, Charsets.UTF_8);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                                                                         inputSupplier
                                                                                 .openStream());

            // Set up authorization code flow
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            httpTransport,
                            jsonFactory,
                            clientSecrets,
                            ImmutableList.of(
                                    GmailScopes.GMAIL_MODIFY,
                                    GmailScopes.GMAIL_READONLY))
                            .setCredentialDataStore(dataStore)
                            .setAccessType("offline")
                            .setApprovalPrompt("auto")
                            .build();
            // First, see if we have a stored credential for the user.
            Credential credential = flow.loadCredential(STACK_EMAIL);
            // If we don't, prompt them to get one.
            if (credential == null) {
                String url = flow.newAuthorizationUrl()
                                 .setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI)
                                 .build();
                System.out.println("Please open the following URL in your browser then "
                                   + "type the authorization code:\n" + url);

                // Read code entered by user.
                System.out.print("Code: ");
                System.out.flush();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(System.in));
                String code = br.readLine();

                // Generate Credential using retrieved code.
                GoogleTokenResponse response = flow.newTokenRequest(code)
                                                   .setRedirectUri(
                                                           GoogleOAuthConstants.OOB_REDIRECT_URI)
                                                   .execute();

                credential =
                        flow.createAndStoreCredential(response, STACK_EMAIL);
            }

            gmail = new Gmail.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            Profile profile = gmail.users()
                                   .getProfile(STACK_EMAIL)
                                   .execute();

            System.out.println(profile.toPrettyString());

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    //
    //            HttpTransport httpTransport = GoogleNetHttpTransport
    //            .newTrustedTransport();
    //            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    //
    //            URL resource = Resources.getResource("Stack.p12");
    //            GoogleCredential credential = new GoogleCredential.Builder()
    //                    .setTransport(httpTransport)
    //                    .setJsonFactory(jsonFactory)
    //                    .setServiceAccountId("stackadmin@stack-of-tasks.iam
    //                    .gserviceaccount.com")
    //                    .setServiceAccountPrivateKeyFromP12File(new File(resource
    //                    .getFile()))
    //                    .setServiceAccountScopes(
    //                            ImmutableList.of(
    //                                    GmailScopes.GMAIL_MODIFY,
    //                                    GmailScopes.GMAIL_READONLY))
    //                    .setServiceAccountUser("stackadmin@stack-of-tasks.com")
    //                    .build();


    public Gmail getGmail() {
        return gmail;
    }

}
