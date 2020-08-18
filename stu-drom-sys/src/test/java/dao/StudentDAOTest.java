package dao;

import junit.framework.TestCase;
import model.Page;
import model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOTest extends TestCase {

    public void testQuery() {
        Page page = new Page();
        page.setSortOrder("asc");
        page.setPageSize(7);
        page.setPageNumber(1);
        List<Student> list = StudentDAO.query(page);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public void testQueryById() {
        Student student = StudentDAO.queryById(2);
        System.out.println(student);
    }

    public void testInsert() {
        Student student = new Student();
        student.setStudentName("tf");
        student.setStudentGraduateYear("20200817");
        student.setStudentMajor("000002001");
        student.setStudentEmail("999@123.com");
        student.setDormId(2);
        StudentDAO.insert(student);
    }

    public void testUpdate() {
        Student student = new Student();
        student.setStudentName("周杰伦");
        student.setStudentGraduateYear("20210715");
        student.setStudentMajor("000002002");
        student.setStudentEmail("2222222@999.com");
        student.setDormId(2);
        student.setId(58);
        StudentDAO.update(student);
    }

    public void testDelete() {
        String[] ids = new String[]{"61", "62"};
        StudentDAO.delete(ids);
    }

    public void testApply() {
        Student student = new Student();
        student.setDormId(4);
        List<Integer> list = new ArrayList<>();
        list.add(60);
        list.add(63);
        student.setIds(list);
        StudentDAO.apply(student);
    }
}