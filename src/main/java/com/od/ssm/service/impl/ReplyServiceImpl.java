package com.od.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.od.ssm.bean.PackagBean_Reply;
import com.od.ssm.bean.PageBean;
import com.od.ssm.mapper.ReplyMapper;
import com.od.ssm.mapper.UserMapper;
import com.od.ssm.po.Reply;
import com.od.ssm.po.User;
import com.od.ssm.service.ReplyService;
import com.od.ssm.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/21.
 */
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;

    //插入一条回复,并且获得最后一页回复的内容
    public String insertReply(Reply reply){
       //插入一条新的回复
        replyMapper.insertReply(reply);
        //获得总的记录数
        Reply temR = new Reply();
        temR.setC_id(reply.getC_id());
        //这里需要根据c_id去获取对应的记录总数
        int count = replyMapper.selectCount(temR);
        //-------------------------------------------------------------------
        int pages = count/5; //每页5条
        if(count%5!=0){
            pages++;
        }


        //pageHelper查最后一页的记录
        PageHelper.startPage(pages, 5);        //查询最后一页的数据
        PageHelper.orderBy("r_id");
      //取得数据集合
       List<Reply> replyList = replyMapper.selectReplyAndUser(reply.getC_id());
       //这里因为reply 跟 comment是 多对一的关系，所以取其中一个拿到comment再获取comment对应的user的u_id即可

            int temU_id = replyList.get(0).getComment().getU_id();
            User temUser = new User();
            temUser.setU_id(temU_id);
            User user = userMapper.selectOne(temUser);//     查询得到mapper对应的user

            //因为 comment 跟user 是 多跟一的关系 ，所以所有的comment都具有相同的user信息
       //(Integer r_id,String r_words,User user,Integer u_id,PageBean PageBean){


      List<PackagBean_Reply>  prList = new ArrayList<PackagBean_Reply>();
        //这里做数据大于1小于五，pages=1的处理(这里pages值用来返回页面添加分页按钮)
        if(count>0&&count<5){
            pages=1;
        }
      PageBean pb = new PageBean();
      pb.setPages(pages);

      //List<Reply>   -->  List<PackagBean_Reply>   <-- PageBean
      for(Reply r:replyList){
          //这里遍历replyList为每个comment的user属性赋值
          r.getComment().setUser(user);
         //转成包装类
          prList.add(new PackagBean_Reply(r.getR_id(),r.getR_words(),r.getUser(),r.getR_u_id(),pb,r.getComment(),r.getC_id(),r.getR_date()));
      }

        JsonMapper jm = new JsonMapper();
        return jm.toJson(prList);       //返回包装类的json数据
    }



  //根据前端传来的pageNum,c_id来获取数据
    public String getReplyByPage(PageBean pageBean){
                //pageNum从前端传过来的数据
        PageHelper.startPage(pageBean.getPageNum(), 5);
        PageHelper.orderBy("r_id");
        //1.获得查询数据
        List<Reply> replyList = replyMapper.selectReplyAndUser(pageBean.getC_id());

        //判断是否为空,为空就只返回只包含 PageBean pages=1 的数据
        if(replyList.size()!=0) {
            //这里因为reply 跟 comment是 多对一的关系，所以取其中一个拿到comment再获取comment对应的user的u_id即可
            int temU_id = replyList.get(0).getComment().getU_id();
            User temUser = new User();
            temUser.setU_id(temU_id);
            User user = userMapper.selectOne(temUser);//     查询得到mapper对应的user
            //--------------------------------------------------------------------------------------------------------
            //2.获得记录总数
            Reply temR = new Reply();
            temR.setC_id(pageBean.getC_id());
            //这里需要根据c_id去获取对应的记录总数
            int count = replyMapper.selectCount(temR);
            //-------------------------------------------------------------------
            int pages = count / 5;

            if (count % 5 != 0) {
                pages++;
            }
            //这里做数据大于1小于五，pages=1的处理(这里pages值用来返回页面添加分页按钮)
            if (count > 0 && count < 5) {
                pages = 1;
            }
            PageBean pb = new PageBean();
            pb.setPages(pages);
            //-------------------------------------------------------------------
            List<PackagBean_Reply> prList = new ArrayList<PackagBean_Reply>();
            //List<Reply>   -->  List<PackagBean_Reply>   <-- PageBean


                for (Reply r : replyList) {
                    //这里遍历replyList为每个comment的user属性赋值
                    r.getComment().setUser(user);
                    prList.add(new PackagBean_Reply(r.getR_id(), r.getR_words(), r.getUser(), r.getR_u_id(), pb, r.getComment(), r.getC_id(), r.getR_date()));
                }

                JsonMapper jm = new JsonMapper();
                return jm.toJson(prList);       //返回包装类的json数据

        }else{
            JsonMapper jm = new JsonMapper();
            PageBean pb = new PageBean();
            pb.setPages(1);

            PackagBean_Reply rtm = new PackagBean_Reply();
            rtm.setC_id(pageBean.getC_id());

            rtm.setPageBean(pb);
            //这里的目的是为了在空集的情况下能够返回 一个json字符串数组，目的是为了格式一致，便于前台获取数据
            List<PackagBean_Reply> prList = new ArrayList<PackagBean_Reply>();
            prList.add(rtm);             //
            prList.add(new PackagBean_Reply());
            return jm.toJson(rtm);       //返回包装类的json数据
        }
    }




}
