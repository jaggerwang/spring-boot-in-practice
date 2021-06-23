package net.jaggerwang.sbip.adapter.dao.mybatis.type;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jagger Wang
 */
public class LongListJsonTypeHandler extends JacksonTypeHandler {
    public LongListJsonTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {
        var l = (List<Integer>)  super.parse(json);
        return l.stream().map(Long::valueOf).collect(Collectors.toList());
    }
}
