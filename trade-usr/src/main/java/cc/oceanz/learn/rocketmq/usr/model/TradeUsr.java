package cc.oceanz.learn.rocketmq.usr.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Trade_Usr")
public class TradeUsr implements Serializable {

    private static final long serialVersionUID = 1884183856184105256L;

    @Id
    @Column(name = "usr_id")
    private Integer           usrId;

    @Column(name = "usr_name")
    private String            usrName;

    @Column(name = "usr_password")
    private String            usrPassword;

    @Column(name = "usr_mobile")
    private String            usrMobile;

    @Column(name = "usr_score")
    private Integer           usrScore;

    @Column(name = "usr_reg_time")
    private Date              usrRegTime;

    @Column(name = "usr_money")
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

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword == null ? null : usrPassword.trim();
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