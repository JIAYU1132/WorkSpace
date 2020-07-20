package util;

import org.junit.Assert;
import org.junit.Test;

public class DBUtilTest {

    @Test
    public void t1() {
        Assert.assertNotNull(DBUtil.getConnection());
    }
}
