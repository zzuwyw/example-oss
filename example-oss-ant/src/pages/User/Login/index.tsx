import { Footer } from '@/components';
import { login } from '@/services/ant-design-pro/api';
import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {
  LoginForm,
  ProFormCheckbox,
  ProFormText,
} from '@ant-design/pro-components';
import { history, useModel, Helmet } from '@umijs/max';
import { message } from 'antd';
import Settings from '../../../../config/defaultSettings';
import { flushSync } from 'react-dom';
import { createStyles } from 'antd-style';
import React from "react";

const useStyles = createStyles(() => {
  return {
    container: {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage: "url(/bg_login.png)",
      backgroundSize: '100% 100%',
    },
  };
});

const Login: React.FC = () => {
  const { initialState, setInitialState } = useModel('@@initialState');
  const { styles } = useStyles();

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      flushSync(() => {
        setInitialState((s) => ({
          ...s,
          currentUser: userInfo,
        }));
      });
    }
  };

  const handleSubmit = async (values: API.LoginParams) => {
    const data = await login(values);
    if (data.status) {
      message.success("登录成功");
      await fetchUserInfo();
      const urlParams = new URL(window.location.href).searchParams;
      history.push(urlParams.get('redirect') || '/');
      return;
    }
  };

  return (
    <div className={styles.container}>
      <Helmet>
        <title>
          登录页
          - {Settings.title}
        </title>
      </Helmet>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" src="/logo.svg" />}
          title="Alex Lab"
          subTitle="Alex Lab 是一个私人后台管理实验平台"
          initialValues={{
            rememberMe: true,
          }}
          onFinish={ handleSubmit }
          /*onFinish={async (values) => {
            console.log(values);
            await handleSubmit(values as API.LoginParams);
          }}*/
        >
          <div style={{
            marginBottom: 20,
            textAlign: 'center'
          }}>通过用户名登录</div>

            <>
              <ProFormText
                name="username"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined />,
                }}
                placeholder="用户名"
                rules={[
                  {
                    required: true,
                    message: "请输入用户名",
                  },
                ]}
              />
              <ProFormText.Password
                name="password"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder="密码"
                rules={[
                  {
                    required: true,
                    message: "请输入密码",
                  },
                ]}
              />
            </>

            <div
              style={{
                marginBottom: 24,
              }}
            >
            <ProFormCheckbox noStyle name="rememberMe">
              <span>记住我</span>
            </ProFormCheckbox>
            <a
              style={{
                float: 'right',
              }}
            >
              <span>忘记了密码 ？</span>
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
