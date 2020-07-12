package yunjiayu;

import org.junit.Assert;
import org.junit.Test;
import yunjiayu.util.DBUtil;

public class DBUtilTest {

    @Test
    public void t1() {
        Assert.assertNotNull(DBUtil.getConnection());
    }
}
