package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@Setter
@Getter
public class Student {
    private Integer id;
    private String studentName;
    private String studentGraduateYear;
    private String studentMajor;
    private String studentEmail;
    private Integer dormId;
    private Date createTime;
    private String buildingName;
    private Integer buildingId;
    private String dormNo;
    private List<Integer> ids;
}
