package hu.albi.back.model;

import javax.persistence.*;

@Entity
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    private Integer subletId;

    private String name;
    private String url;

    public FileInfo(){

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubletId() {
        return subletId;
    }

    public void setSubletId(Integer subletId) {
        this.subletId = subletId;
    }
}
