package cn.liuyiyou.blog.model.Bo;

import cn.liuyiyou.blog.model.Vo.CommentVo;
import lombok.Data;

import java.util.List;

@Data
public class CommentBo extends CommentVo {

    private int levels;
    private List<CommentVo> children;

    public CommentBo(CommentVo comments) {
        setAuthor(comments.getAuthor());
        setMail(comments.getMail());
        setCoid(comments.getCoid());
        setAuthorId(comments.getAuthorId());
        setUrl(comments.getUrl());
        setCreated(comments.getCreated());
        setAgent(comments.getAgent());
        setIp(comments.getIp());
        setContent(comments.getContent());
        setOwnerId(comments.getOwnerId());
        setCid(comments.getCid());
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public List<CommentVo> getChildren() {
        return children;
    }

    public void setChildren(List<CommentVo> children) {
        this.children = children;
    }
}
