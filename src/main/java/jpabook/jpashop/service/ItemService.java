package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {//사실상 아이템리파지토리에 위임만 하는 클래스.
    //경우에 따라서는 위임만 하는 클래스를 따로 만들 필요가 있을지 고민해봐야 함
    //그런 경우에는 그냥 컨트롤러에서 리파지토리에 바로 접근해서 사용해도 문제가 없다고도 봄

    private final ItemRepository itemRepository;

    @Transactional //메소드에 달아놓은게 더 우선권임을 이용한 오버라이딩
    public void saveItem(Item item) {
        itemRepository.save(item);
    }
    @Transactional
    public void updateItem(Long itemId, UpdateItemDto updateItemDto) {
        Item findItem = itemRepository.findOne(itemId);//리파지토리에서 직접 DB 데이터를 가져왔기때문에 영속 엔티티
        //1단계
//        findItem.setPrice(price);
//        findItem.setName(name);
//        findItem.setStockQuantity(stockQuantity);

        //2단계(좀 더 개선된 버전)
//        findItem.setPrice(updateItemDto.getPrice());
//        findItem.setName(updateItemDto.getName());
//        findItem.setStockQuantity(updateItemDto.getStocQuantity());

        //사실 findItem.change(price, name, stockQuantity);나
        //findItem.addStock(~~)이렇게 메서드를 통해서 변경하는게 좋음
        //setter같은건 코드 추적이 어려워 유지보수가 구데기

        //3단계(이상적인 코드)
        findItem.change(updateItemDto); //검토 필요

    //@Transactional 어노테이션으로 인해 해당 메서드가 끝나면 트랜잭션 커밋이 일어나고,
    //해당 시점에 Flushing 과정에서 변경감지(Dirty Checking)가 일어나 JPA가 알아서 반영
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
