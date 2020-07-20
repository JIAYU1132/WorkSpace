package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;

@Getter
@Setter
@ToString
public class Dorm {
    private Integer id;
    private String dormNo;
    private String dormDesc;
    private Integer buildingID;
    private Date createTime;
}
