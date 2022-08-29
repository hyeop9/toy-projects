package study.board.web.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.board.domain.item.DeliveryCode;
import study.board.domain.item.Item;
import study.board.repository.ItemRepository;
import study.board.domain.item.ItemType;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 상품 Controller
 */
@Controller
@RequiredArgsConstructor    // final 붙은 멤버변수 생성자 자동 생성
@RequestMapping("/itemList")
public class ItemController {

    private final ItemRepository itemRepository;

    /* 상품 목록 */
    @GetMapping
    public String itemList(Model model) {
        List<Item> itemList = itemRepository.findAll();
        model.addAttribute("itemList", itemList);
        return "item/itemList";
    }

    /* 상품 상세 */
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {    // PathVariable 로 넘어온 상품 ID로 상품을 조회하고 모델에 담는다.
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item/item";
    }

    /* 상품 등록 뷰 템플릿 호출 */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());     // th:object 적용을 위해 빈 오프젝트 생성 후 뷰에 전달
        return "item/addForm";
    }

    /* 상품 등록 처리*/
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item")
                            Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "item/addForm";
        }
        // 성공 로직
        item.setItemName(item.getItemName());
        item.setPrice(item.getPrice());
        item.setQuantity(item.getQuantity());

        itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/itemList/{itemId}";
    }

    /* 상품 수정 뷰 템플릿 호출 */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "item/editForm";
    }

    /* 상품 수정 처리 */
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                        @Validated @ModelAttribute("item")
                        Item item,
                        BindingResult bindingResult) {

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "item/editForm";
        }

        item.setItemName(item.getItemName());
        item.setPrice(item.getPrice());
        item.setQuantity(item.getQuantity());

        itemRepository.update(itemId, item);
        return "redirect:/itemList/{itemId}";
    }

    /* 등록 지역 선택 */
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    /* 상품 종류 선택 */
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    /* 배송 방식 선택 */
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }


    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}
