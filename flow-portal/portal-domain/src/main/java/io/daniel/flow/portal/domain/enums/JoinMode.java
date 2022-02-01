package io.daniel.flow.portal.domain.enums;

/**
 * 合并模式
 *
 * @author neason-cn
 * @date 2022/2/1
 */
public enum JoinMode {

    /**
     * incoming中任何一条分支成功运行，就可以运行该节点
     */
    WAIT_ANY,

    /**
     * incoming所有分支都成功运行结束才能运行该节点
     */
    WAIT_ALL
}
