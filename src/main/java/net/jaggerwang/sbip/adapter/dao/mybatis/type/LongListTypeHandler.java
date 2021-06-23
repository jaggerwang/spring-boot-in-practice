package net.jaggerwang.sbip.adapter.dao.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jagger Wang
 */
public class LongListTypeHandler extends BaseTypeHandler<List<Long>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Long> parameter,
                                    JdbcType jdbcType) throws SQLException {
        var list = new ArrayList<String>();
        for (Long item : parameter) {
            list.add(String.valueOf(item));
        }
        ps.setString(i, String.join(",", list));
    }

    @Override
    public List<Long> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        }

        return Arrays.stream(str.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        }

        return Arrays.stream(str.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        }

        return Arrays.stream(str.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}
