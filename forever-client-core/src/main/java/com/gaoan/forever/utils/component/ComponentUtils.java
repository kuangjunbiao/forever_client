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
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;

import com.gaoan.forever.combobox.KeyValComboBox;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.TableModel;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.gson.JsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.github.pagehelper.PageInfo;

public class ComponentUtils {

	/**
	 * 初始化指定下拉列表
	 * 
	 * @param box
	 * @param list
	 */
	public static void initBoxData(JComboBox<String> box, List<String> list) {
		if (box == null || CollectionUtils.isEmpty(list)) {
			return;
		}
		box.removeAllItems();
		for (String str : list) {
			box.addItem(str);
		}
	}

	/**
	 * 初始化指定下拉列表 key-value
	 * 
	 * @param box
	 * @param list
	 */
	public static void initBoxData(KeyValComboBox<KeyValBoxVo> box, List<Map<String, String>> list) {
		if (box == null || CollectionUtils.isEmpty(list)) {
			return;
		}
		box.removeAllItems();
		KeyValBoxVo keyVal;
		for (Map<String, String> map : list) {
			keyVal = new KeyValBoxVo(map.get("key"), map.get("value"));
			box.addItem(keyVal);
		}
	}

	/**
	 * 初始化页面底部块
	 * 
	 * @param pageInfo
	 * @param url
	 * @param conditionModel
	 * @param jf
	 * @param tableModel
	 * @param mainBox
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static <T, V> Box initBottomBox(PageInfo<T> pageInfo, String url, V conditionModel, JFrame jf,
			TableModel<T> tableModel, Box mainBox) {
		Box bottomBox = Box.createHorizontalBox();
		JPanel panel = new JPanel(null);

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

			JButton firstPageBtn = new JButton(MessageInfoConstant.FIRST_PAGE);
			JButton previousPageBtn = new JButton(MessageInfoConstant.PREVIOUS_PAGE);

			firstPageBtn.setBounds(330, 30, 80, 30);
			previousPageBtn.setBounds(420, 30, 80, 30);

			panel.add(firstPageBtn);
			panel.add(previousPageBtn);
			// 第一页和上一页的按钮生成
			if (!isFirst && pageTotal != 0) {
				bindEvent(firstPageBtn, url, firstPage, pageSize, conditionModel, jf, tableModel, mainBox);
				bindEvent(previousPageBtn, url, prePage, pageSize, conditionModel, jf, tableModel, mainBox);
			}

			// 当前页,每页数据,总数据标签生成
			JLabel currPageLable = new JLabel(curPage + "/" + pageTotal);
			currPageLable.setBounds(510, 30, 20, 30);
			JLabel pageSizeLable = new JLabel("每页" + pageSize + "条");
			pageSizeLable.setBounds(540, 30, 80, 30);
			JLabel totalLable = new JLabel("共" + total + "条数据");
			totalLable.setBounds(600, 30, 80, 30);

			panel.add(currPageLable);
			panel.add(pageSizeLable);
			panel.add(totalLable);

			JButton nextPageBtn = new JButton(MessageInfoConstant.NEXT_PAGE);
			JButton lastPageBtn = new JButton(MessageInfoConstant.LAST_PAGE);
			nextPageBtn.setBounds(670, 30, 80, 30);
			lastPageBtn.setBounds(760, 30, 90, 30);
			panel.add(nextPageBtn);
			panel.add(lastPageBtn);
			// 下一页和最后一页按钮的生成
			if (!isLast) {
				bindEvent(nextPageBtn, url, nextPage, pageSize, conditionModel, jf, tableModel, mainBox);
				bindEvent(lastPageBtn, url, lastPage, pageSize, conditionModel, jf, tableModel, mainBox);
			}
		}

		bottomBox.add(panel);

		return bottomBox;
	}

	/**
	 * 绑定翻页按钮事件
	 * 
	 * @param btn
	 * @param url
	 * @param page
	 * @param pageSize
	 * @param conditionModel
	 * @param jf
	 * @param tableModel
	 * @param mainBox
	 */
	public static <T, V> void bindEvent(JButton btn, String url, int page, int pageSize, V conditionModel, JFrame jf,
			TableModel<T> tableModel, Box mainBox) {
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PageInfo<T> pageInfo = sendReq(url, page, pageSize, conditionModel, jf, tableModel);
				// 移除页码区域,重新渲染
				mainBox.remove(2);
				mainBox.add(initBottomBox(pageInfo, url, conditionModel, jf, tableModel, mainBox));
				mainBox.repaint();
			}
		});

	}

	/**
	 * 翻页请求后，重新加载展示数据
	 * 
	 * @param url
	 * @param page
	 * @param pageSize
	 * @param conditionModel
	 * @param jf
	 * @param tableModel
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, V> PageInfo<T> sendReq(String url, int page, int pageSize, V conditionModel, JFrame jf,
			TableModel<T> tableModel) {
		// 获取内容显示部分的对象class
		Class clazz = null;
		List<T> data = tableModel.getData();
		if (CollectionUtils.isNotEmpty(data)) {
			clazz = data.get(0).getClass();
		}

		url = String.format(url + "?page=%s&pageSize=%s", page, pageSize);
		Map<String, Object> map = CallUtils.post(jf, url, GsonUtils.toJson(conditionModel), MessageEnum.SEARCH_MSG);

		PageInfo<T> pageInfo = null;
		List<T> list = null;
		List<T> newList = new ArrayList<T>();

		if (map == null || map.isEmpty()) {
			list = new ArrayList<T>();
		} else {
			pageInfo = JsonUtils.fromJson(map.get("pageInfo"), PageInfo.class);

			if (pageInfo != null) {
				list = pageInfo.getList();
				if (CollectionUtils.isNotEmpty(list)) {
					for (T temp : list) {
						newList.add((T) GsonUtils.fromJson(temp, clazz));
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
