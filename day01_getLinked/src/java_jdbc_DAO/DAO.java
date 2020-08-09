package java_jdbc_DAO;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import java.sql.Connection;

import java_jdbc_GetDatabaseLinked.JDBCTools;

public class DAO {

	// insert update delete
	public void updata(String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(null, ps, conn);
		}
	}

	public <T> T get(Class<T> clazz, String sql, Object... args) {
		List<T> result = getForList(clazz, sql, args);
		if (result.size() > 0) {
			return result.get(0);
		}

		return null;
	}

	/**
	 * ���ݴ����sql����ȡ�������
	 * 
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
		List<T> list = new ArrayList<T>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			// 1.�õ������resultSet
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			resultSet = ps.executeQuery();
			// 2.��������������MapΪԪ�صļ���Vaules��
			List<Map<String, Object>> values = resultSetToMapList(resultSet);
			// 3.����ת����Map����ת��ΪStudent����
			list = mapListToBeanList(clazz, values);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCTools.release(resultSet, ps, conn);
		}
		return list;
	}

	private <T> List<T> mapListToBeanList(Class<T> clazz, List<Map<String, Object>> values)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		// 11.�ж�List�Ƿ�Ϊ�ռ��ϣ�����Ϊ�գ������List
		// �õ�һ��һ��Map�����ٰ�ÿ��Map����ת��Ϊһ��Class��Ӧ��Object����
		List<T> result = new ArrayList<>();

		T bean = null;

		if (values.size() > 0) {
			for (Map<String, Object> m : values) {
				bean = clazz.newInstance();
				for (Map.Entry<String, Object> entry : m.entrySet()) {
					String firldName = entry.getKey();
					Object fieldValue = entry.getValue();

					BeanUtils.setProperty(bean, firldName, fieldValue);
				}
				// 12.��Object������뵽list��
				result.add(bean);
			}
		}
		return result;
	}

	/**
	 * ��resultSet���������Ϊһ�����ж���Map��¼��List����
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String, Object>> resultSetToMapList(ResultSet resultSet) throws SQLException {
		// 5.׼��List<Map<String,Object>,����һ��Map�������һ����¼
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();

		List<String> columnLabels = getColumnLables(resultSet);
		Map<String, Object> map = null;
		// 7.����ResultSet��ʹ��whileѭ��
		while (resultSet.next()) {
			map = new HashMap<String, Object>();

			for (String columaLabel : columnLabels) {
				Object columnValue = resultSet.getObject(columaLabel);
				map.put(columaLabel, columnValue);
			}
			values.add(map);
		}
		return values;
	}

	/**
	 * ��ȡ�����ColumnLabel��Ӧ�ļ���
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<String> getColumnLables(ResultSet rs) throws SQLException {
		List<String> labels = new ArrayList<>();

		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			labels.add(rsmd.getColumnLabel(i + 1));
		}

		return labels;
	}

	// ����ĳ����¼��ĳһ���ֶε�ֵ �� һ��ͳ�Ƶ�ֵ
	@SuppressWarnings("unchecked")
	public <E> E getForValue(String sql, Object... args) {

		// 1.�õ���������ý����Ӧ��ֻ��һ�У���ֻ��һ��
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;

		try {
			// 1.�õ������resultSet
			conn = JDBCTools.getConnection();
			ps = conn.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				return (E) resultSet.getObject(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.release(resultSet, ps, conn);
		}
		return null;
	}

}
