package com.od.ssm.mapper;

        import com.od.ssm.po.BookTable;
        import com.od.ssm.po.User;
        import tk.mybatis.mapper.common.Mapper;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */
public interface BookTableMapper extends Mapper<BookTable> {
        public int selectCountByBookNameAndPhone(BookTable bt);


}
