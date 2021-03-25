package test.nal.test.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum work status
 * @author Admin
 *
 */
@AllArgsConstructor
@Getter
public enum StatusWork {
    Planing ("1", "planning"),
    Doing ("2","Doing"),
    Complete("3","Complete");
    
    private String code;
    private String display;
}
