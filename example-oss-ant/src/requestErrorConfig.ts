﻿import type { RequestConfig } from '@umijs/max';
import { message } from 'antd';

interface Error {
  errorMessage?: string;
}

/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const errorConfig: RequestConfig = {
  // 错误处理： umi@3 的错误处理方案。
  errorConfig: {
    // 错误抛出
    errorThrower: (e) => {
      const { errorMessage } = e as unknown as Error;
      const error: any = new Error(errorMessage);
      error.name = 'BizError';
      error.info = { errorMessage };
      throw error; // 抛出自制的错误
    },
    // 错误接收及处理
    errorHandler: (error: any, options: any) => {
      if (options?.skipErrorHandler) throw error;
      // 我们的 errorThrower 抛出的错误。
      if (error.name === 'BizError') {
        const errorInfo: Error | undefined = error.info;
        if (errorInfo) {
          const { errorMessage } = errorInfo;
          message.warning(errorMessage);
        }
      } else if (error.response) {
        // Axios 的错误
        // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
        const errorMessage = error.response.data.errorMessage;
        message.error(errorMessage);
        // message.error(`Response status:${error.response.status}`);
      } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        // \`error.request\` 在浏览器中是 XMLHttpRequest 的实例，
        // 而在node.js中是 http.ClientRequest 的实例
        message.error('请求已经成功发起，但没有收到响应，请重试！');
      } else {
        // 发送请求时出了点问题
        message.error('请求发送错误，请重试！');
      }
    },
  },

};
