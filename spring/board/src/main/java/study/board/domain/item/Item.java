package study.board.domain.item;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 상품 객체
 */
@Data
public class Item {

    private Long id;            // 상품 번호
    @NotBlank
    private String itemName;    // 상품 이름
    @NotNull
    private Integer price;      // 상품 가격
    @NotNull
    private Integer quantity;   // 상품 수량

    private Boolean open;           // 판배 여부
    private List<String> regions;   // 등록 지역
    private ItemType itemType;      // 상품 종류
    private String deliveryCode;    // 배송 방식

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}