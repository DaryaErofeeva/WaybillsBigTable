package db;

import models.Waybill;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;

import java.io.IOException;
import java.util.List;

public abstract class AbstractDAO {
    public void createTable(String tableName) {
        try (Connection connection = BigtableHelper.getConnection()) {
            connection
                    .getAdmin()
                    .createTable(new HTableDescriptor(TableName.valueOf(tableName)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTable(String tableName) {
        try (Connection connection = BigtableHelper.getConnection()) {
            connection
                    .getAdmin()
                    .disableTable(TableName.valueOf(tableName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public abstract void createFamily(String familyName);

    public abstract void deleteFamily(String familyName);

    public abstract void set(String rowName, String familyName, String columnName, String value);

    public abstract void deleteRow(String rowName);
}
