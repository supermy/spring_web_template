package com.supermy.utils;


import java.io.Serializable;
import java.text.MessageFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class Pages implements Serializable {
	
	public static int PAGE_SIZE = 30; //显示数目
	public int pageSize = PAGE_SIZE; // 当页显示数目
	private int totalCount; // 总记录数
	private int currentPage; //当前页
	

	public Pages() {
	}


	public Pages(int currentPage, int pageSize) {
		this.currentPage =currentPage;
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 
	 * @description getTotalPage(获取页码数)
	 * @conditions (总个数存在，当前页显示数目存在)
	 * @return int
	 * @exception
	 * @since 1.0.0
	 */
	public int getTotalPage() {
		if (this.getTotalCount() == 0)
			return 0;
		int totalPage = (this.getTotalCount() + pageSize - 1)
				/ pageSize;
		return totalPage;
	}


	
	public String toString() {
		// 开发模式
		return ToStringBuilder.reflectionToString(this);
		// 生产模式
		// return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
		// this.pkId).toString();
	}
	

	/**
	 * 
	 * @description main(测试)
	 * @conditions
	 * @param args
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void main(String[] args) {
//		String realUrl = "/product/list.htm?typeLevel=yiji&displayMode=default&goodsTypeId=01&cpage=2";
//		String srcUrlPattern = "/product/list.htm\\?typeLevel=(.*)&displayMode=(.*)&goodsTypeId=([0-9]{2,6})(&cpage=([0-9]+))?";
//		String destUrlPattern = "/product/list/$1/$2_$3_{0}.html";
//		String tempUrl = realUrl.replaceAll(srcUrlPattern, destUrlPattern);
//		System.out.println(tempUrl);
		
		if ("com.ccc.scsk.persistence.newweb.SortRelationMapper.selectByExample".matches(".*findByPage.*")) {
			 System.out.println("ddddddddddddd1");
		} else {
			 System.out.println("----------");
		}
	   
	}
}
