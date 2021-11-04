package com.example.crytocurrency_ltdt;

public class News {
    private int count;
    private  String kind;
    private  String domain;
    private String  title_post;
    private String published_at;
    private String url;
    private  String Code_cryto;
    private String title_cryto;
    public int getCount() {
        return count;
    }

    public String getKind() {
        return kind;
    }

    public String getDomain() {
        return domain;
    }

    public String getTitle_post() {
        return title_post;
    }

    public String getPublished_at() {
        return published_at;
    }

    public String getUrl() {
        return url;
    }

    public String getCode_cryto() {
        return Code_cryto;
    }

    public String getTitle_cryto() {
        return title_cryto;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setTitle_post(String title_post) {
        this.title_post = title_post;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCode_cryto(String code_cryto) {
        Code_cryto = code_cryto;
    }

    public void setTitle_cryto(String title_cryto) {
        this.title_cryto = title_cryto;
    }

    public News(String title_post, String published_at, String domain) {
        this.title_post = title_post;
        this.published_at = published_at;
        this.domain=domain;
    }
}
