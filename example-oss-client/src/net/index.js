import axios from "axios";
import { ElMessage } from "element-plus";

const defaultErrorHandler = (message) => ElMessage.error(message);
const defaultFailureHandler = (code, message) => ElMessage.warning('错误码:' + code + '，错误信息:' + message);
const defaultFinallyHandler = () => {};

function post(url, params, successHandler, failureHandler = defaultFailureHandler, errorHandler = defaultErrorHandler, finallyHandler = defaultFinallyHandler) {
    axios.post(url, params, {
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.code === 200) {
            successHandler(data.data);
        } else {
            failureHandler(data.code, data.message);
        }
    }).catch((error) => {
        handleError(error, errorHandler);
    }).then(() => {
        console.log('finally');
        finallyHandler();
    })
}

function get(url, successHandler, failureHandler = defaultFailureHandler, errorHandler = defaultErrorHandler, finallyHandler = defaultFinallyHandler) {
    axios.get(url,{
        withCredentials: true
    }).then(({ data }) => {
        if (data.code === 200) {
            successHandler(data.data);
        } else {
            failureHandler(data.code, data.message);
        }
    }).catch((error) => {
        handleError(error, errorHandler);
    }).then(() => {
        finallyHandler();
    })
}

// 请求响应拦截，异常统一处理
/*axios.interceptors.response.use(response => {
    return response;
}, error => {
    handleError(error, defaultErrorHandler);
    return Promise.reject(error);
});*/

function handleError(error, errorHandler) {
    // 确保错误是AxiosError类型
    if (error instanceof axios.AxiosError) {
        // 可以访问错误的属性，如error.code, error.message, error.response
        if (error.response) {
            // 请求已发出，服务器用响应返回
            // console.log('HTTP Error:', error.response.status);
            const response = error.response;
            const httpStatus = response.status;
            if (httpStatus === 401 || httpStatus === 403) {
                errorHandler(response.data.message);
            } else {
                errorHandler('发生错误，请联系管理员');
            }
        } else if (error.request) {
            // 请求已发出但没有收到响应
            // 可能是网络问题，或请求被中断
            // console.log('Network Error:', error.request);
            errorHandler('服务异常，请联系管理员');
        } else {
            // 在构建请求时出错
            // console.log('Configuration Error:', error.message);
            errorHandler('设置请求错误，请联系管理员');
        }
    } else {
        // 处理非AxiosError的错误
        // console.log('Unknown Error:', error);
        errorHandler('未知错误，请联系管理员');
    }
}

export { get, post, defaultFailureHandler, defaultErrorHandler }