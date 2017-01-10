package com.sz.hm.core.base.model;

public class Pager {
	
	/*
	 * ÿҳ��ʾ����
	 */
	private int pageSize;
	
	/*
	 * ��ǰҳ��
	 */
	private int pageNum;
	
	/*
	 * ��¼����
	 */
	private int totalCount;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * ������ҳ��
	 * @return
	 */
	public int getPageCount(){
		return totalCount%pageSize>0?totalCount/pageSize+1:totalCount/pageSize;
	}
	
	/**
	 * �����ҳ����ʼҳ
	 * @return
	 */
	public int getPageDispayStart(){

		int startGap = Math.min(pageNum-1,2);
		int endGap = Math.min(getPageCount()-pageNum,2);
		
		if(startGap<=endGap){
			return Math.max(pageNum-startGap,1);
		}else{
			return Math.max(pageNum-(4-endGap),1);
		}
		
	}
	
	/**
	 * �����ҳ������ҳ
	 * @return
	 */
	public int getPageDispayEnd(){
		int startGap = Math.min(pageNum-1,2);
		int endGap = Math.min(getPageCount()-pageNum,2);
		
		if(startGap<=endGap){
			return Math.min(pageNum+(4-startGap),getPageCount());
		}else{
			return Math.min(pageNum+endGap,getPageCount());
		}
	}
}
