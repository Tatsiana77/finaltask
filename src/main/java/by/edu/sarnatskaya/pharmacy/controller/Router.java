package by.edu.sarnatskaya.pharmacy.controller;


import by.edu.sarnatskaya.pharmacy.controller.command.PagePath;

/**
 * author Tatsiana Sarnatskaya
 *
 * The {@link Router} class contains four fields:
 * pagePath
 * routeType
 * errorCode
 * errorMessage
 * that are used with by controller to find out where and how
 * request and response should be processed after the controller.
 */


public class Router {
    public enum RouterType{
        FORWARD, REDIRECT
    }
    private String pagePath = PagePath.INDEX_PAGE;
    private RouterType routerType = RouterType.FORWARD;
    private Integer errorCode;
    private String errorMessage;

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public RouterType getRouterType() {
        return routerType;
    }

    public void setRouterType(RouterType routerType) {
        this.routerType = routerType;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
