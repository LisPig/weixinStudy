package com.ego.core.page;

/**
 * 分页算法类
 */
public class Pagination {

    private static final long serialVersionUID = 10001444444L;
    /**
     * 默认为10
     */
    public static int DEF_PAGE_SIZE = 10;
    protected int totalCount = 0;
    protected int pageSize = 0;
    protected int currentPage = 1;

    /**
     * 构造器
     * <p>
     * @param currentPage 当前页码
     * @param pageSize 每页几条数据
     * @param totalCount 总共几条数据
     */
    public Pagination(int totalCount, int pageSize, int currentPage) {
        setTotalCount(totalCount);
        setPageSize(pageSize);
        setCurrentPage(currentPage);
        adjustCurrentPage();
    }

    /**
     * 调整页码，使不超过最大页数
     */
    public void adjustCurrentPage() {
        if (currentPage < 1) {
            this.currentPage = 1;
        }
        int tp = getTotalPage();
        if (currentPage > tp) {
            currentPage = tp;
        }
    }

    /**
     * 调整每页显示记录数，小于<1或大于记录总数则 pagesize为记录总数
     */
    public void adjustPageSize() {
        if (this.pageSize < 1 || this.pageSize > this.getTotalCount()) {
            this.pageSize = this.getTotalCount();
        }
    }

    /**
     * 获得当前页码
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * if currentPage < 1 then currentPage=1 <p>
     * @param current
     *
     * Page
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.adjustCurrentPage();
    }

    /**
     * 每页几条数据
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * if pageSize< 1 显示全部 <p>
     * @
     *
     * param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 总共几条数据
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * if totalCount<0 then totalCount=0 <p>
     * @param total
     *
     * Count
     */
    public void setTotalCount(int totalCount) {
        if (totalCount < 0) {
            this.totalCount = 0;
        } else {
            this.totalCount = totalCount;
        }
    }

    /**
     * 总共几页,至少有一页
     */
    public int getTotalPage() {
        if (this.totalCount <= 0) {
            return 1;
        }
        this.adjustPageSize();
        int totalPage = totalCount / pageSize;
        if (totalPage == 0 || totalCount % pageSize != 0) {
            totalPage++;
        }
        return totalPage;
    }

    /**
     * 是否第一页
     */
    public boolean isFirstPage() {
        return currentPage <= 1;
    }

    /**
     * 是否最后一页
     */
    public boolean isLastPage() {
        return currentPage >= getTotalPage();
    }

    /**
     * 下一页页码
     */
    public int getNextPage() {
        if (isLastPage()) {
            return currentPage;
        } else {
            return currentPage + 1;
        }
    }

    /**
     * 上一页页码
     */
    public int getPrePage() {
        if (isFirstPage()) {
            return currentPage;
        } else {
            return currentPage - 1;
        }
    }

    /**
     * 检查页码 checkCurrentPage
     * <p>
     * @param currentPage
     * <p>
     * @return if currentPage==null or currentPage<1 then return 1 else return
     * currentPage
     */
    public static int checkCurrentPage(Integer currentPage) {
        return (currentPage == null || currentPage < 1) ? 1 : currentPage;
    }

    public static void main(String[] a) {
        System.out.print("---" + 11 / 3);
    }
}
