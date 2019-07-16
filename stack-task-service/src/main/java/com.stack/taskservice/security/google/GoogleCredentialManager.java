package com.stack.taskservice.security.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GoogleCredentialManager {
    private DataStore<StoredCredential> dataStore;

    private GoogleCredentialManager() {

        try {
            dataStore = MemoryDataStoreFactory.getDefaultInstance().getDataStore(
                    "credentialDatastore");
        } catch (IOException e) {
            throw new RuntimeException("Unable to create in memory credential datastore",
                                       e);
        }
    }

    public GoogleCredential getCredential(String username) throws Exception {
        try {
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(new NetHttpTransport())
                    .setJsonFactory(new JacksonFactory())
                    .addRefreshListener(
                            new DataStoreCredentialRefreshListener(
                                    username, dataStore))
                    .build();

            if (dataStore.containsKey(username)) {
                StoredCredential storedCredential = dataStore.get(username);
                credential.setAccessToken(storedCredential.getAccessToken());
                //credential.setRefreshToken(storedCredential.getRefreshToken());
            } else {
                //Do something of your own here to obtain the access token.
                //Most usually redirect the user to the OAuth page
            }

            return credential;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("isuue while setting credentials", e);
        }
    }

    public void saveCredential(String username, Credential credential) {
        StoredCredential storedCredential = new StoredCredential();
        storedCredential.setAccessToken(credential.getAccessToken());
        // storedCredential.setRefreshToken(credential.getRefreshToken());
        try {
            dataStore.set(username, storedCredential);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
