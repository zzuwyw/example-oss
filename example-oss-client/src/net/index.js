import axios from "axios";
import {ElMessage} from "element-plus";

const defaultError = (message) => ElMessage.error(message);
const defaultFailure = (code, message) => ElMessage.warning('错误码:' + code + '，错误信息:' + message);

function post(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            "Content-Type": 'application/x-www-form-urlencoded'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.code === 200) {
            success(data.data);
        } else {
            failure(data.code, data.message);
        }
    }).catch((data) => {
        const response = data.response;
        const httpStatus = response.status;
        if (httpStatus === 401 || httpStatus === 403) {
            error(response.data.message);
        } else {
            error('发生错误，请联系管理员');
        }
    })
}

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url,{
        withCredentials: true
    }).then(({data}) => {
        if (data.code === 200) {
            success(data.data);
        } else {
            failure(data.code, data.message);
        }
    }).catch(error)
}

export {get, post}