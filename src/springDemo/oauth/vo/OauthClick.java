package springDemo.oauth.vo;

import java.util.Date;

public class OauthClick {
    private Integer id;

    private String clickId;

    private String clickName;

    private String clientSecret;

    private Date insertTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId == null ? null : clickId.trim();
    }

    public String getClickName() {
        return clickName;
    }

    public void setClickName(String clickName) {
        this.clickName = clickName == null ? null : clickName.trim();
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret == null ? null : clientSecret.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}