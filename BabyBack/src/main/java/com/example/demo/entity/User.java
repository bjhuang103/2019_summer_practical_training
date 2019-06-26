package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String account;//账号
    private String passWord;//密码
    private String name;//姓名
    private String tel;//联系电话
    private String email;//邮箱
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<LostBaby> lostBabies;//发布的走丢儿童信息
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<MatchBaby> matchBabies;//发布的可能匹配的儿童信息
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Article> articles;//发布的文章
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Comment> comments;//发布的可能匹配的儿童信息

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<LostBaby> getLostBabies() {
        return lostBabies;
    }

    public void setLostBabies(Set<LostBaby> lostBabies) {
        this.lostBabies = lostBabies;
    }

    public Set<MatchBaby> getMatchBabies() {
        return matchBabies;
    }

    public void setMatchBabies(Set<MatchBaby> matchBabies) {
        this.matchBabies = matchBabies;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
