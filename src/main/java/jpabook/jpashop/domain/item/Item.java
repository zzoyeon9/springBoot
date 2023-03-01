package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.service.UpdateItemDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@BatchSize(size = 100)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    // 데이터를 변경할 일이 있으면 setter를 사용하거나 바깥에서 변경하지말고
    // 해당 데이터가 있는 위치에서 핵심 비즈니스 메서드를 가지고 변경하도록 하자.
    /*
    stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /*
    stock 감소
     */
   public void removeStock(int quantity) {
        int restStock = this.stockQuantity -= quantity;
        if(restStock<0)
            throw new NotEnoughStockException("need more stock");
        this.stockQuantity = restStock;
    }

    public void change(UpdateItemDto updateItemDto) {
        updateItemDto.setPrice(getPrice());
        updateItemDto.setStockQuantity(getStockQuantity());
        updateItemDto.setName(getName());
    }
}
