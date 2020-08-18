package dao;

import junit.framework.TestCase;
import model.Building;
import model.DictionaryTag;

import java.util.ArrayList;
import java.util.List;

public class BuildingDAOTest extends TestCase {

    public void testQuery() {
        List<DictionaryTag> list = BuildingDAO.query();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}