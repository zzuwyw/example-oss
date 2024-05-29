import { AvatarDropdown, AvatarName } from '@/components';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import {RunTimeLayoutConfig} from '@umijs/max';
import { history } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import { errorConfig } from './requestErrorConfig';
import React from 'react';
import { currentUser } from "@/services/ant-design-pro/api";
const loginPath = '/user/login';

export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: API.CurrentUser;
  loading?: boolean;
  fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
}> {
  const fetchUserInfo = async () => {
    try {
      //const msg = await queryCurrentUser({
      //   skipErrorHandler: true,
      // });
      // return msg.data;
      const msg = await currentUser({});
      const username = msg.data.username;
      const registerAt = msg.data.registerAt;
      console.log('username', username);
      console.log('registerAt', registerAt);

      return {
        userid:'00000001',
        avatar:'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png',
        username: username,
        registerAt: registerAt,
      };
      //return {username:'Alex', userid:'00000001', avatar:'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png', registerAt:12345678465};
    } catch (error) {
      history.push(loginPath);
    }
    return undefined;
  };

  const { location } = history;

  // 如果不是登录页面，执行
  if (location.pathname !== loginPath) {
    const currentUser = await fetchUserInfo();
    return {
      fetchUserInfo,
      currentUser,
      settings: defaultSettings as Partial<LayoutSettings>,
    };
  }
  return {
    fetchUserInfo,
    settings: defaultSettings as Partial<LayoutSettings>,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
//export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {
export const layout: RunTimeLayoutConfig = ({ initialState }) => {
  return {
    avatarProps: {
      src: initialState?.currentUser?.avatar,
      title: <AvatarName />,
      render: (_, avatarChildren) => {
        return <AvatarDropdown>{avatarChildren}</AvatarDropdown>;
      },
    },
    onPageChange: () => {
      const { location } = history;
      // 如果没有登录，重定向到 login
      if (!initialState?.currentUser && location.pathname !== loginPath) {
        history.push(loginPath);
      }

      if (initialState?.currentUser && location.pathname === loginPath) {
        history.push('/');
      }
    },
    // 背景图片
    bgLayoutImgList: [
      {
        src: '/layout_bg_nav.webp',
        bottom: 0,
        left: 0,
        width: '331px',
      },
    ],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
        </>
      );
    },
    ...initialState?.settings,
  };
};

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request = {
  timeout: 6 * 10000,
  ...errorConfig,
};
