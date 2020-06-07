package cf.zhul.appdemoforcas.vos;

import lombok.Data;

/**
 * @author zhul
 * @date 2020/6/6 00:54
 * @description
 */
@Data
public class Result<T> {
    private int code = 200;
    private String msg = "success";
    private T data;

    public Result(T data) {
        this.data = data;
    }
}
