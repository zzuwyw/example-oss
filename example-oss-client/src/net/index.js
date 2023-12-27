import axios from "axios";
import {ElMessage} from "element-plus";

const defaultError = () => ElMessage.error('发生错误，请联系管理员');
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
    }).catch(error)
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