package dao;

import junit.framework.TestCase;
import model.DictionaryTag;

import java.util.List;

public class DictionaryTagDAOTest extends TestCase {

    public void testQuery() {
        List<DictionaryTag> list = DictionaryTagDAO.query("000001");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


    }
}