package cn.liuyiyou.blog.model.Bo;

import cn.liuyiyou.blog.model.Vo.ContentVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArchiveBo implements Serializable {

    private String date;
    private String count;
    private List<ContentVo> articles;
}
