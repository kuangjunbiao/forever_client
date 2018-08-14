package com.gaoan.forever.utils.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.collections.CollectionUtils;

import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.TableModel;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.github.pagehelper.PageInfo;

public class ComponentUtils {

    /**
     * 初始化指定下拉列表
     * 
     * @param box
     * @param list
     */
    public static void initBoxData(JComboBox<String> box, List<Object> list) {
	if (box == null || CollectionUtils.isEmpty(list)) {
	    return;
	}
	box.removeAllItems();
	String str;
	for (Object obj : list) {
	    str = (String) obj;
	    box.addItem(str);
	}
    }

    @SuppressWarnings("deprecation")
    public static <T, V> Box initBottomBox(PageInfo<T> pageInfo, String url, V obj, JFrame jf, TableModel<T> tableModel,
	    Box mainBox) {
	Box bottomBox = Box.createHorizontalBox();

	long total;
	int curPage;
	int pageSize;
	int pageTotal;
	int prePage;
	int nextPage;
	int firstPage;
	int lastPage;
	boolean isFirst = false;
	boolean isLast = false;

	if (pageInfo != null) {
	    total = pageInfo.getTotal();
	    curPage = pageInfo.getPageNum();
	    pageSize = pageInfo.getPageSize();
	    pageTotal = pageInfo.getPages();
	    prePage = pageInfo.getPrePage();
	    nextPage = pageInfo.getNextPage();
	    firstPage = pageInfo.getFirstPage();
	    lastPage = pageInfo.getLastPage();
	    isFirst = pageInfo.isIsFirstPage();
	    isLast = pageInfo.isIsLastPage();

	    JLabel currPageLable = new JLabel(curPage + "/" + pageTotal);
	    JLabel pageSizeLable = new JLabel("每页" + pageSize + "条");
	    JLabel totalLable = new JLabel("共" + total + "条数据");

	    if (!isFirst) {
		JButton previousPageBtn = new JButton(MessageInfoConstant.PREVIOUS_PAGE);
		JButton firstPageBtn = new JButton(MessageInfoConstant.FIRST_PAGE);
		bottomBox.add(firstPageBtn);
		bottomBox.add(previousPageBtn);

		bindEvent(firstPageBtn, url, firstPage, pageSize, obj, jf, tableModel, mainBox);
		bindEvent(previousPageBtn, url, prePage, pageSize, obj, jf, tableModel, mainBox);
	    }

	    bottomBox.add(currPageLable);
	    bottomBox.add(Box.createHorizontalStrut(5));
	    bottomBox.add(pageSizeLable);
	    bottomBox.add(Box.createHorizontalStrut(5));
	    bottomBox.add(totalLable);

	    if (!isLast) {
		JButton nextPageBtn = new JButton(MessageInfoConstant.NEXT_PAGE);
		JButton lastPageBtn = new JButton(MessageInfoConstant.LAST_PAGE);
		bottomBox.add(nextPageBtn);
		bottomBox.add(lastPageBtn);

		bindEvent(nextPageBtn, url, nextPage, pageSize, obj, jf, tableModel, mainBox);
		bindEvent(lastPageBtn, url, lastPage, pageSize, obj, jf, tableModel, mainBox);
	    }
	}

	return bottomBox;
    }

    public static <T, V> void bindEvent(JButton btn, String url, int page, int pageSize, V obj, JFrame jf,
	    TableModel<T> tableModel, Box mainBox) {
	btn.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		PageInfo<T> pageInfo = sendReq(url, page, pageSize, obj, jf, tableModel);
		mainBox.remove(2);
		mainBox.add(initBottomBox(pageInfo, url, obj, jf, tableModel, mainBox));
		mainBox.repaint();
	    }
	});

    }

    public static <T, V> PageInfo<T> sendReq(String url, int page, int pageSize, V obj, JFrame jf,
	    TableModel<T> tableModel) {
	// 获取内容显示部分的对象class
	Class<? extends Object> clazz = null;
	List<T> data = tableModel.getData();
	if (CollectionUtils.isNotEmpty(data)) {
	    clazz = data.get(0).getClass();
	}

	url = String.format(url + "?page=%s&pageSize=%s", page, pageSize);
	Map<String, Object> map = CallUtils.post(jf, url, GsonUtils.toJson(obj), MessageEnum.SEARCH_MSG);

	PageInfo<T> pageInfo = null;
	List<T> list = null;
	List<T> newList = new ArrayList<T>();

	if (map == null || map.isEmpty()) {
	    list = new ArrayList<T>();
	} else {
	    pageInfo = GsonUtils.fromJson(map.get("pageInfo"), PageInfo.class);

	    if (pageInfo != null) {
		list = pageInfo.getList();
		if (CollectionUtils.isNotEmpty(list)) {
		    for (T temp : list) {
			newList.add(GsonUtils.fromJson(temp, clazz));
		    }
		}
	    }

	    // 初始化tableModel
	    tableModel.setData(newList);
	    tableModel.fireTableDataChanged();
	}

	return pageInfo;
    }

}
