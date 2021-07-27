package com.lxd.util;

import java.util.List;

/**
 * Author: Cris
 * Date: 2021/07/02
 * Time: 11:23
 * Project: blog
 **/
public class Pagination {
    private static final long serialVersionUID = -8741766802354222579L;

    private int pageSize; // 每页显示多少条记录

    private int currentPage; //当前第几页数据

    private int totalRecord; // 一共多少条记录

    private int totalPage; // 一共多少页

    private List dataList; //要显示的数据

    public Pagination(int pageNum, int pageSize, List sourceList) {
        if (sourceList == null || sourceList.isEmpty()) {
            return;
        }

        // 总记录条数
        this.totalRecord = sourceList.size();

        // 每页显示多少条记录
        this.pageSize = pageSize;

        //获取总页数
        this.totalPage = this.totalRecord / this.pageSize;
        if (this.totalRecord % this.pageSize != 0) {
            this.totalPage = this.totalPage + 1;
        }

        // 当前第几页数据
        this.currentPage = this.totalPage < pageNum ? this.totalPage : pageNum;

        // 起始索引
        int fromIndex = this.pageSize * (this.currentPage - 1);

        // 结束索引
        int toIndex = this.pageSize * this.currentPage > this.totalRecord ? this.totalRecord : this.pageSize * this.currentPage;

        this.dataList = sourceList.subList(fromIndex, toIndex);
//        this.dataList = sourceList;
    }


    public Pagination(int pageSize, int currentPage, int totalRecord, int totalPage,
                    List dataList) {
        super();
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalRecord = totalRecord;
        this.totalPage = totalPage;
        this.dataList = dataList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

}