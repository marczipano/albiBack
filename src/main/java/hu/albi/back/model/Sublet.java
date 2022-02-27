package hu.albi.back.model;

import javax.persistence.*;

@Entity
public class Sublet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    private Integer sellerId;   // id of seller
    private String address;     // location of sublet
    private Integer size;       // size in m2
    private Boolean garden;     // has garden or not
    private Integer rooms;      // number of rooms
    private String desc;        // detailed description
    private Integer price;      // price in HUF/month

    public Sublet(Integer sellerId,
                  String address,
                  Integer size,
                  Boolean garden,
                  Integer rooms,
                  String desc,
                  Integer price){
        this.sellerId = sellerId;
        this.address = address;
        this.size = size;
        this.garden = garden;
        this.rooms = rooms;
        this.desc = desc;
        this.price = price;
    }

    public Sublet(){
    }

    public Integer getId() {
        return id;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getGarden() {
        return garden;
    }

    public void setGarden(Boolean garden) {
        this.garden = garden;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "Sublet{"+
                "id=" + id +
                ", sellerId='" + sellerId + '\'' +
                ", address='" + address + '\'' +
                ", size='" + size + '\'' +
                ", garden='" + garden + '\'' +
                ", rooms='" + rooms + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                "}";
    }
}
