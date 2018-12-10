package cn.liuyiyou.blog.model.Bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatisticsBo implements Serializable {

    private Long articles;
    private Long comments;
    private Long links;
    private Long attachs;


}
