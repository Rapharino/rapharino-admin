package org.rapharino.base.api.param;

/**
 * 分页入参
 */
public class PagerParam extends BaseParam {

    private static final long serialVersionUID = -7839818183685177138L;
    /**
     * 查询页码
     */
    private Integer page;
    /**
     * 查询分页大小
     */
    private Integer pageSize;
    /**
     * 是否分页
     */
    private boolean pageable;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }

    @Override
    public String toString() {
        return "PagerParam{" +
            "page=" + page +
            ", pageSize=" + pageSize +
            ", pageable=" + pageable +
            '}';
    }

    /**
     * 分页:page!=null pageSize!=null
     * @return
     */
    public boolean verify() {
        if (isPageable() && (null == getPage() || null == getPageSize()))
            return false;
        else
            return true;
    }
}
