package jpabook.jpashop.controller;


import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        //사실 아래보다는 createBook 이런 스테틱 생성자 메서드를 갖는게 좋음 실무에서는 setter는 다 날
        Book book = new Book();
        book.setStockQuantity(form.getStockQuantity());
        book.setPrice(form.getPrice());
        book.setAuthor(form.getAuthor());
        book.setName(form.getName());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";

    }

    @GetMapping("/items/{itemId}/edit")//@PathVariable을 통해 매핑시켜
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);//예제 단순화를 위한 캐스팅. 실제론 좋지 않음
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setAuthor(item.getAuthor());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")//
    public String updateItem(@ModelAttribute("form") BookForm form) {//

//        Book book = new Book();
//        book.setId(form.getId());//id가 들어있기때문에 실무에서는 유저가 item에 대해서 권한이 있는지 없는지 체크해주는 로직이 있어야함
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
        //사실 Controller에서 위에처럼 어설프게 엔티티 생성하지 말자
         //어설프게 엔티티를 파라미터로 쓰지 않고 원하는 데이터만 업데이트
         //만약 업데이트할 데이터가 많으면 서비스 계층에 dto만들어서 쓰자

        UpdateItemDto dto = new UpdateItemDto();

        dto.setName(form.getName());
        dto.setPrice(form.getPrice());
        dto.setStockQuantity(form.getStockQuantity());

        //itemService.updateItem(itemId, dto);


        return "redirect:/items";
    }
}
