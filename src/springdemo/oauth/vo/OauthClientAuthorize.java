package springdemo.oauth.vo;

import java.util.Date;

public class OauthClientAuthorize {
    private String id;

    private String clientId;

    private Integer codeAuthorizeId;

    private String userId;

    private Date insertTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public Integer getCodeAuthorizeId() {
        return codeAuthorizeId;
    }

    public void setCodeAuthorizeId(Integer codeAuthorizeId) {
        this.codeAuthorizeId = codeAuthorizeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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