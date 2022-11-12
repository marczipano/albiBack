package hu.albi.back.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Sublet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;
    private Integer sellerId;   // id of seller
    private String address;     // location of sublet
    private Integer size;       // size in m2
    private Integer garden;     // has garden or not
    private Integer rooms;      // number of rooms
    @Size(max = 1024)
    private String descript;        // detailed description
    private Integer price;      // price in HUF/month

    public Sublet(Integer sellerId,
                  String address,
                  Integer size,
                  Integer garden,
                  Integer rooms,
                  String descript,
                  Integer price) {
        this.sellerId = sellerId;
        this.address = address;
        this.size = size;
        this.garden = garden;
        this.rooms = rooms;
        this.descript = descript;
        this.price = price;
    }

    public Sublet() {
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

    public Integer getGarden() {
        return garden;
    }

    public void setGarden(Integer garden) {
        this.garden = garden;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public String getDesc() {
        return descript;
    }

    public void setDesc(String desc) {
        this.descript = desc;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Sublet{" +
                "id=" + id +
                ", sellerId='" + sellerId + '\'' +
                ", address='" + address + '\'' +
                ", size='" + size + '\'' +
                ", garden='" + garden + '\'' +
                ", rooms='" + rooms + '\'' +
                ", desc='" + descript + '\'' +
                ", price='" + price + '\'' +
                "}";
    }
}
