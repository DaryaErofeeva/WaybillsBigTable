package db;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.varia.NullAppender;

import java.io.IOException;

public class BigtableHelper {
    private static Connection connection;

    private static void connect() throws IOException {
        GoogleCredential credential = GoogleCredential.getApplicationDefault();
        BasicConfigurator.configure(new NullAppender());

        Configuration config = HBaseConfiguration.create();
        connection = ConnectionFactory.createConnection(config);
    }

    public static Connection getConnection() throws IOException {
        if (connection == null) connect();
        return connection;
    }
}
