package interfaces;

import models.Waybill;

public interface AccountingBook {
    void add(Waybill waybill);

    void edit(Waybill waybill);

    void delete(Waybill waybill);
}
