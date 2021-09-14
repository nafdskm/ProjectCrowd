package com.skm.crowd.util;

/**
 *  统一整个项目中Ajax请求返回的结果
 *  也可以用于分布式架构中各个模块调用时返回统一类型
 * @param <T>
 */
public class ResultEntity<T> {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    // 用来封装当前请求结果是成功还是失败
    private String result;
    // 请求处理失败返回的错误消息
    private String message;
    // 要返回的数据
    private T data;

    /**
     *  请求处理成功且不需要返回数据
     * @return
     */
    public static <E> ResultEntity<E> successWithoutData() {
        return new ResultEntity<E>(SUCCESS, null, null);
    }

    /**
     *  请求处理成功且不需要返回数据，返回提示消息
     * @return
     */
    public static <E> ResultEntity<E> successWithoutDataWithMessage(String msg) {
        return new ResultEntity<E>(SUCCESS, msg, null);
    }

    /**
     *  请求处理成功且需要返回数据
     * @param data  需要返回的数据
     * @param <E>   需要返回数据的数据类型
     * @return
     */
    public static <E> ResultEntity<E> successWithData(E data) {
        return new ResultEntity<E>(SUCCESS, null, data);
    }

    /**
     *  请求处理失败
     * @param message   失败的处理消息
     * @return
     */
    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity<E>(FAILED, message, null);
    }

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
