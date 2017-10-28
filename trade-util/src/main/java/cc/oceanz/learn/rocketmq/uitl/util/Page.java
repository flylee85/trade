package cc.oceanz.learn.rocketmq.uitl.util;

import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwz on 2017/10/28 07:01.
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 649153232837808481L;

    private int               page;
    private int               size;
    private long              total;

    private List<T>           list             = new ArrayList<>();

    public Page(Class<T> clazz, PageInfo<?> pageInfo) {
        if (pageInfo != null) {
            try {
                this.page = pageInfo.getPageNum();
                this.size = pageInfo.getSize();
                this.total = pageInfo.getTotal();

                for (Object o : pageInfo.getList()) {
                    T t = clazz.newInstance();
                    BeanUtils.copyProperties(t, o);
                    list.add(t);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
