package model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 宿舍楼
 */
@Getter
@Setter
@ToString
public class Building {
    
    private Integer id;

    /**
     * 名称
     */
    private String buildingName;

    /**
     * 描述
     */
    private String buildingDesc;

    /**
     * 创建时间
     */
    private Date createTime;
}