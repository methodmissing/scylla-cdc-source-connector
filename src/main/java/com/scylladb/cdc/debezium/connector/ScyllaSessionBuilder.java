package com.scylladb.cdc.debezium.connector;

import com.scylladb.cdc.cql.CQLConfiguration;
import com.scylladb.cdc.cql.driver3.Driver3Session;

public class ScyllaSessionBuilder {
    private final ScyllaConnectorConfig configuration;

    public ScyllaSessionBuilder(ScyllaConnectorConfig configuration) {
        this.configuration = configuration;
    }

    public Driver3Session build() {
        CQLConfiguration.Builder builder = CQLConfiguration.builder();
        builder.addContactPoints(configuration.getContactPoints());
        if (configuration.getUser() != null && configuration.getPassword() != null) {
            builder.withCredentials(configuration.getUser(), configuration.getPassword());
        }
        if (configuration.getTruststoreLocation() != null && configuration.getTruststorePassword() != null && configuration.getTruststoreType() != null &&
                   configuration.getKeystoreLocation() != null && configuration.getKeystorePassword() != null && configuration.getKeystoreType() != null) {
            builder.withClientCertAuth(configuration.getTruststoreLocation(), configuration.getTruststorePassword(), configuration.getTruststoreType(),
                                       configuration.getKeystoreLocation(), configuration.getKeystorePassword(), configuration.getKeystoreType());
        }
        builder.withConsistencyLevel(configuration.getConsistencyLevel());
        if (configuration.getLocalDCName() != null) {
            builder.withLocalDCName(configuration.getLocalDCName());
        }
        return new Driver3Session(builder.build());
    }
}