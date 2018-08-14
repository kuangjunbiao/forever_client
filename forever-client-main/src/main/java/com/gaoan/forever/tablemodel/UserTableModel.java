package com.gaoan.forever.tablemodel;

import java.util.List;

import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.TableModel;
import com.gaoan.forever.model.UserInfoModel;

public class UserTableModel extends TableModel<UserInfoModel> {

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
		return 7;
	}

	@Override
	public String getColumnName(int column) {
		// 根据实际情况返回列名
		switch (column) {
		case 0:
			return "编号";
		case 1:
			return "用户名";
		case 2:
			return "密码";
		case 3:
			return "真实姓名";
		case 4:
			return "状态";
		case 5:
			return "创建时间";
		case 6:
			return "修改时间";
		default:
			return "";
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		UserInfoModel model = data.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return model.getId();
		case 1:
			return model.getUserName();
		case 2:
			return model.getPassword();
		case 3:
			return model.getRealName();
		case 4:
			String statusDesc = MessageInfoConstant.USER_NORMAL_STATUS;
			if (ForeverConstant.USER_LOCK_STATUS.equals(String.valueOf(model.getStatus()))) {
				statusDesc = MessageInfoConstant.USER_LOCK_STATUS;
			}
			return statusDesc;
		case 5:
			return model.getCreateDate();
		case 6:
			return model.getUpdateDate();
		default:
			return "";
		}
	}

	public void setData(List<UserInfoModel> data) {
		this.data = data;
	}

}