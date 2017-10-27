package cc.oceanz.learn.rocketmq.protocol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lwz on 2017/10/20 13:53.
 */
public class TradeUsrRet implements Serializable {

    private static final long serialVersionUID = 1884183856184105256L;

    private Integer           usrId;
    private String            usrName;
    private String            usrMobile;
    private Integer           usrScore;
    private Date              usrRegTime;
    private BigDecimal        usrMoney;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName == null ? null : usrName.trim();
    }

    public String getUsrMobile() {
        return usrMobile;
    }

    public void setUsrMobile(String usrMobile) {
        this.usrMobile = usrMobile == null ? null : usrMobile.trim();
    }

    public Integer getUsrScore() {
        return usrScore;
    }

    public void setUsrScore(Integer usrScore) {
        this.usrScore = usrScore;
    }

    public Date getUsrRegTime() {
        return usrRegTime;
    }

    public void setUsrRegTime(Date usrRegTime) {
        this.usrRegTime = usrRegTime;
    }

    public BigDecimal getUsrMoney() {
        return usrMoney;
    }

    public void setUsrMoney(BigDecimal usrMoney) {
        this.usrMoney = usrMoney;
    }
}