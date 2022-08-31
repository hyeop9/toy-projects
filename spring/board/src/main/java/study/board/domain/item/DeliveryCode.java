package study.board.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 배송 방식
 *
 * FAST: 빠른 배송
 * NORMAL: 일반 배송
 * SLOW: 느린 배송
 */
@Data
@AllArgsConstructor
public class DeliveryCode {
    private String code;        // FAST, NORMAL, SLOW
    private String displayName; // 빠른 배송, 일반 배송, 느린 배송
}