package dao;

import junit.framework.TestCase;
import model.DictionaryTag;

import java.util.ArrayList;
import java.util.List;

public class DormDAOTest extends TestCase {

    public void testQuery() {
        List<DictionaryTag> list = DormDAO.query(1);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}