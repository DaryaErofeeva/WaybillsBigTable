package db;

import javafx.collections.FXCollections;
import models.Producer;
import models.Waybill;
import models.WaybillGood;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaybillsDAO extends AbstractDAO {
    private static final String TABLE_NAME = "waybills";

    private static final String PRODUCER = "producer";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";

    private static final String WAYBILL_GOODS = "waybill_goods";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String AMOUNT = "amount";

    @Override
    public void createFamily(String familyName) {
        try (Connection connection = BigtableHelper.getConnection()) {
            connection
                    .getAdmin()
                    .addColumn(TableName.valueOf(TABLE_NAME),
                            new HColumnDescriptor(familyName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFamily(String familyName) {
        try (Connection connection = BigtableHelper.getConnection()) {
            connection
                    .getAdmin()
                    .deleteColumn(TableName.valueOf(TABLE_NAME),
                            Bytes.toBytes(familyName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(String rowName, String familyName, String columnName, String value) {
        try (Connection connection = BigtableHelper.getConnection()) {
            Put put = new Put(Bytes.toBytes(rowName));
            put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
            connection.getTable(TableName.valueOf(TABLE_NAME)).put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(Waybill waybill) {
        try (Connection connection = BigtableHelper.getConnection()) {
            String rowName = String.valueOf(waybill.getNumber());
            Put put = new Put(Bytes.toBytes(rowName));

            put.addColumn(Bytes.toBytes(PRODUCER), Bytes.toBytes(NAME), Bytes.toBytes(waybill.getProducer().getName()));
            put.addColumn(Bytes.toBytes(PRODUCER), Bytes.toBytes(PHONE), Bytes.toBytes(waybill.getProducer().getPhone()));
            put.addColumn(Bytes.toBytes(PRODUCER), Bytes.toBytes(ADDRESS), Bytes.toBytes(waybill.getProducer().getAddress()));

            WaybillGood waybillGood;
            for (int i = 0; i < waybill.getWaybillGoods().size(); i++) {
                waybillGood = waybill.getWaybillGoods().get(i);
                put.addColumn(Bytes.toBytes(WAYBILL_GOODS), Bytes.toBytes(TITLE + (i + 1)), Bytes.toBytes(waybillGood.getTitle()));
                put.addColumn(Bytes.toBytes(WAYBILL_GOODS), Bytes.toBytes(PRICE + (i + 1)), Bytes.toBytes(String.valueOf(waybillGood.getPrice())));
                put.addColumn(Bytes.toBytes(WAYBILL_GOODS), Bytes.toBytes(AMOUNT + (i + 1)), Bytes.toBytes(String.valueOf(waybillGood.getAmount())));
            }
            connection.getTable(TableName.valueOf(TABLE_NAME)).put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRow(String rowName) {
        try (Connection connection = BigtableHelper.getConnection()) {
            connection
                    .getAdmin()
                    .deleteColumn(TableName.valueOf(TABLE_NAME),
                            Bytes.toBytes(rowName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Waybill> readAll() {
        List<Waybill> waybills = new ArrayList<>();
        try (Connection connection = BigtableHelper.getConnection()) {
            Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
            Scan scan = new Scan();
            ResultScanner scanner = table.getScanner(scan);
            for (Result row : scanner) {
                waybills.add(getWaybill(row));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return waybills;
    }

    private Waybill getWaybill(Result row) {
        Waybill waybill = new Waybill();
        waybill.setNumber(Integer.valueOf(Bytes.toString(row.getRow())));
        waybill.setProducer(new Producer(Bytes.toString(row.getValue(Bytes.toBytes(PRODUCER), Bytes.toBytes(NAME))),
                Bytes.toString(row.getValue(Bytes.toBytes(PRODUCER), Bytes.toBytes(PHONE))),
                Bytes.toString(row.getValue(Bytes.toBytes(PRODUCER), Bytes.toBytes(ADDRESS)))));

        int i = 1;
        List<WaybillGood> waybillGoods = new ArrayList<>();
        while (true) {
            if (!row.containsColumn(Bytes.toBytes(WAYBILL_GOODS), Bytes.toBytes(TITLE + i))) break;
            waybillGoods.add(new WaybillGood(Bytes.toString(row.getValue(Bytes.toBytes(WAYBILL_GOODS), Bytes.toBytes(TITLE + i))),
                    Double.valueOf(Bytes.toString(row.getValue(Bytes.toBytes(WAYBILL_GOODS), Bytes.toBytes(PRICE + i)))),
                    Double.valueOf(Bytes.toString(row.getValue(Bytes.toBytes(WAYBILL_GOODS), Bytes.toBytes(AMOUNT + i))))));
            i++;
        }
        waybill.setWaybillGoods(FXCollections.observableArrayList(waybillGoods));
        return waybill;
    }

    public Waybill read(String rowName) {
        Waybill waybill = new Waybill();
        try (Connection connection = BigtableHelper.getConnection()) {
            Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

            Get get = new Get(Bytes.toBytes(rowName));
            Scan scan = new Scan(get);
            ResultScanner scanner = table.getScanner(scan);
            for (Result row : scanner) {
                waybill = getWaybill(row);
                break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return waybill;
    }
}
