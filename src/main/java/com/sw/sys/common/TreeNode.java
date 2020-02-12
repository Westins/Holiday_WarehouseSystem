package com.sw.sys.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 树节点
 * @author: sw
 * @time: 2020/2/11 14:20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    /**
     *  {
     *       "title": "其他页面",
     *       "icon": "&#xe630;",
     *       "href": "",
     *       "spread": false,
     *       "children": [
     *         {
     *           "title": "404页面",
     *           "icon": "&#xe61c;",
     *           "href": "page/404.html",
     *           "spread": false
     *         }
     *       ]
     *     }
     *
     */
    private Integer id;
    @JsonProperty("parentId")
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private List<TreeNode> children = new ArrayList<TreeNode>();

    /**
     * 首页左边导航器
     * 构造器
     */
    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }
}
