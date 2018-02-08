package io.github.ztgoto.cloud.auth.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.ztgoto.cloud.auth.Run;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Run.class })
public class MasterSlaveTest {

	@Autowired
	DataSource dataSource;

	@Test
	public void testMasterSlave() throws SQLException {

		String sql = "insert test(name,age) values('test_master1',3)";
		
		Connection conn = dataSource.getConnection();

		PreparedStatement ps = conn.prepareStatement(sql);

		int row = ps.executeUpdate();

		System.out.println(">>>>insert row:" + row);

		ps.close();
		conn.close();

		sql = "select * from test limit 5";
		
		conn = dataSource.getConnection();

		ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			System.out.println(">>>>:" + rs.getString("name"));

		}
		rs.close();
		ps.close();
		conn.close();
	}

}
