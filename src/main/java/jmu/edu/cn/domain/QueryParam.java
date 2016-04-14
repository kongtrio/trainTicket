package jmu.edu.cn.domain;

/**
 * Created by Administrator on 2016/3/21.
 */
public class QueryParam {
    private String username;
    private Users users;
    private String sites;
    private String province;
    private String searchKey;
    private String beginSite;
    private String endSite;
    private String time;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBeginSite() {
        return beginSite;
    }

    public void setBeginSite(String beginSite) {
        this.beginSite = beginSite;
    }

    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
