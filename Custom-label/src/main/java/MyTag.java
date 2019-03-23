import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class MyTag extends SimpleTagSupport {

	private Integer id; // tld文件当中的自定义标签的属性，这里的属性和类型必须要和tld文件当中的属性和类型一致
	private String name;

	/**
	 * get set方法 这里可以不需要
	 *
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void doTag() throws JspException, IOException {

		List<MyTag> list = geMyTags();
		JspWriter out = getJspContext().getOut();
		StringBuffer buffer = new StringBuffer();

		buffer.append("<select>");
		for (MyTag myTag : list)

		{
			buffer.append("<option value=").append(myTag.getId()).append(">").append(myTag.getName())
					.append("</option>");
		}
		buffer.append("</select>");

		out.print(buffer.toString());
	}

	/**
	 * 查询数据
	 * 
	 * @return
	 */
	public List<MyTag> geMyTags() {
		List<MyTag> list = new ArrayList<>();// 创建一个list集合
		try {
			Connection conn = getConn();// 链接数据库
			String sql = "select * from city;";// 查询语句
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);// 预编译sql
			ResultSet rs = ps.executeQuery();// 返回一个结果集并执行sql
			while (rs.next()) {
				// MyTag tag = new MyTag(); // 创建一个对象
				MyTag myTag = new MyTag();
				myTag.setId(rs.getInt(1));// 将查询出来的信息添加到对象当中
				myTag.setName(rs.getString(2));
				list.add(myTag);// 将对象添加到list当中

			}
			/**
			 * 关闭对象和数据库链接
			 */

			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 链接数据库
	 * 
	 * @return
	 */
	public Connection getConn() {
		Connection conn = null;
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 链接数据库
			conn = (Connection) DriverManager.getConnection("jdbc:Mysql://localhost:3306/myself", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}