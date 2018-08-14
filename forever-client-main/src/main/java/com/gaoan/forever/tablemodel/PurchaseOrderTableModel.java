package com.gaoan.forever.tablemodel;

import java.util.List;

import com.gaoan.forever.model.PurchaseOrderInfoModel;
import com.gaoan.forever.model.TableModel;

public class PurchaseOrderTableModel extends TableModel<PurchaseOrderInfoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = -4597895409030660817L;

    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public int getColumnCount() {
	// 根据实际情况返回列数
	return 9;
    }

    @Override
    public String getColumnName(int column) {
	// 根据实际情况返回列名
	switch (column) {
	case 0:
	    return "进货编号";
	case 1:
	    return "进货单名称";
	case 2:
	    return "物品名称";
	case 3:
	    return "进货价格";
	case 4:
	    return "数量";
	case 5:
	    return "总价";
	case 6:
	    return "创建人";
	case 7:
	    return "创建时间";
	case 8:
	    return "修改时间";
	default:
	    return "";
	}
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	PurchaseOrderInfoModel book = data.get(rowIndex);
	switch (columnIndex) {
	case 0:
	    return book.getId();
	case 1:
	    return book.getPurchaseOrderName();
	case 2:
	    return book.getGoodsName();
	case 3:
	    return book.getCostPrice();
	case 4:
	    return book.getQuantity();
	case 5:
	    return book.getTotal();
	case 6:
	    return book.getRealName();
	case 7:
	    return book.getCreateTime();
	case 8:
	    return book.getUpdateTime();
	default:
	    return "";
	}
    }

    public void setData(List<PurchaseOrderInfoModel> data) {
	this.data = data;
    }
}