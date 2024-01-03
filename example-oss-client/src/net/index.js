import axios from "axios";
import {ElMessage} from "element-plus";

const defaultErrorHandler = (message) => ElMessage.error(message);
const defaultFailureHandler = (code, message) => ElMessage.warning('错误码:' + code + '，错误信息:' + message);

function post(url, data, success, failureHandler = defaultFailureHandler, errorHandler = defaultErrorHandler) {
    axios.post(url, data, {
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.code === 200) {
            success(data.data);
        } else {
            failureHandler(data.code, data.message);
        }
    }).catch((error) => {
        handleError(error, errorHandler);
    })
}

function get(url, success, failureHandler = defaultFailureHandler, errorHandler = defaultErrorHandler) {
    axios.get(url,{
        withCredentials: true
    }).then(({data}) => {
        if (data.code === 200) {
            success(data.data);
        } else {
            failureHandler(data.code, data.message);
        }
    }).catch((error) => {
        handleError(error, errorHandler);
    })
}

function handleError(error, errorHandler) {
    const response = error.response;
    const httpStatus = response.status;
    if (httpStatus === 401 || httpStatus === 403) {
        errorHandler(response.data.message);
    } else {
        errorHandler('发生错误，请联系管理员');
    }
}

export {get, post}