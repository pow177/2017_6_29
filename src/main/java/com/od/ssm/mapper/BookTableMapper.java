package com.od.ssm.mapper;

        import com.github.abel533.mapper.Mapper;
        import com.od.ssm.po.BookTable;
        import com.od.ssm.po.User;
        import org.jboss.logging.Param;

        import java.util.List;


/**
 * Created by 黄冠莳 on 2017/7/10.
 */
public interface BookTableMapper extends Mapper<BookTable> {
        public int selectCountByBookNameAndPhone(BookTable bt);
        public void insertListBookTables(List<BookTable> bookTableList);


}
