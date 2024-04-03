<template>
  <div class="root">
    <div class="layout-body">
      <div class="form-login-container">
        <div class="form-login-top">
          <div class="form-login-header">
            <div class="form-login-logo">
              <IconLogo/>
            </div>
            <div class="form-login-title">
              <span>Alex Lab</span>
            </div>
          </div>
          <div class="form-login-desc">
            <span>Alex Lab 是一个私人后台管理实验平台</span>
          </div>
        </div>
        <div class="form-login-main">
          <el-form
              ref="loginForm"
              class="from-login"
              status-icon>
            <div class="from-login-type">
              <span>用户名密码登录</span>
            </div>
            <el-form-item prop="username">
              <el-input class="form-login-input"
                  @keyup.enter="login()"
                  v-model="loginForm.username"
                  type="text"
                  placeholder="用户名"
                  size="large"
                  :clearable=true
                  :prefix-icon="User"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input class="form-login-input"
                  @keyup.enter="login()"
                  v-model="loginForm.password"
                  type="password"
                  placeholder="密码"
                  size="large"
                  :show-password=true
                  :prefix-icon="Lock"></el-input>
            </el-form-item>
            <el-form-item>
              <el-checkbox @keyup.enter="login()" v-model="loginForm.rememberMe">
                <span>记住我的用户名</span>
              </el-checkbox>
              <el-link class="form-login-forget-psd"><span>忘记了密码？</span></el-link>
            </el-form-item>
            <el-form-item>
              <el-button type="primary"
                         @click="login()"
                         size="large"
                         :auto-insert-space="true"
                         class="form-login-btn">登录</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <footer class="layout-footer">
      <div class="global-footer">
        <div class="global-footer-copyright">
          <span class="icon-copyright"><IconCopyright/></span> Powered by Alex Lab
        </div>
      </div>
    </footer>
  </div>

<!--  <div style="text-align: center;margin: 50px;">
    <div style="margin-top: 80px">
      <el-image src="./src/assets/logo.svg" style="width: 80px;"></el-image>
      <div>
        <span>项目实验室</span>
      </div>
    </div>
    <div style="font-size: 25px;margin-top: 80px">
      <span>登录</span>
      <div style="margin-top: 30px;">
        <el-input @keyup.enter="login()" v-model="form.username" type="text" placeholder="用户名" :prefix-icon="User">
        </el-input>
        <el-input @keyup.enter="login()" v-model="form.password" type="password" placeholder="密码" :prefix-icon="Lock" style="margin-top: 30px">
        </el-input>
      </div>

      <div style="margin-top: 10px">
        <el-row>
          <el-col :span="12" style="text-align: left">
            <el-checkbox @keyup.enter="login()" v-model="form.rememberMe"><span style="font-size: 12px">记住我</span></el-checkbox>
          </el-col>
          <el-col :span="12" style="text-align: right">
            <el-link><span style="font-size: 12px">忘记密码？</span></el-link>
          </el-col>
        </el-row>
      </div>

      <div style="margin-top: 30px">
        <el-button @click="login()" type="primary" style="width: 270px">确认登录</el-button>
      </div>
<!-      <el-divider>
        <span style="color: gray;font-size: 12px">没有账号</span>
      </el-divider>
      <div>
        <el-button type="warning" style="width: 270px">注册账号</el-button>
      </div>->
    </div>
  </div>-->


</template>

<script setup>

import {Lock, User} from "@element-plus/icons-vue";
import {reactive } from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net/index.js";
import router from "@/router/index.js";
import {usePrincipalStore} from "@/stores/principal.js";
import IconCopyright from "@/components/icons/IconCopyright.vue";
import IconLogo from "@/components/icons/IconLogo.vue";

const loginForm = reactive({
  username: '',
  password: '',
  rememberMe: false
});

const principalStore = usePrincipalStore();

const login = () => {
  if (!loginForm.username || !loginForm.password) {
    ElMessage.warning('请填写用户名和密码');
  } else {
    post('/auth/login', {
      username: loginForm.username,
      password: loginForm.password,
      rememberMe: loginForm.rememberMe
    }, () => {
      get('/auth/getPrincipal', (data) => {
        principalStore.principal.userDetail = data;
        router.push("/home");
      }, () => {
        principalStore.principal.userDetail = null;
      })
    })
  }
}

</script>

<style scoped>
.root {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  background-image: url('../assets/images/login-bg.png');
  background-size: 100% 100%;
}

.layout-body {
  flex: 1 1 0;
  padding: 32px 0;
}

.form-login-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding-inline: 32px;
  padding-block: 24px;
  overflow: auto;
  background: inherit;
}

.form-login-top {
  text-align: center;
}

.form-login-header {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 44px;
  line-height: 44px;
}

.form-login-logo {
  width: 44px;
  height: 44px;
  margin-inline-end: 16px;
  vertical-align: top;
}

.form-login-title {
  position: relative;
  inset-block-start: 2px;
  font-weight: 600;
  font-size: 33px;
}

.form-login-desc {
  margin-block-start: 12px;
  margin-block-end: 40px;
  color: rgba(0, 0, 0, 0.65);
  font-size: 14px;
}


.form-login-main {
  width: 328px;
  min-width: 280px;
  max-width: 75vw;
  margin: 0 auto;
}

.from-login {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  color: rgba(0, 0, 0, 0.88);
  font-size: 14px;
  line-height: 1.5714285714285714;
}

.from-login-type {
  padding: 12px;
  text-align: center;
  font-size: 16px;
}

.form-login-forget-psd {
  margin-left: 120px;
  color: dodgerblue;
}

/deep/ .el-input__prefix {
  color: rgba(0, 0, 0, 0.88);
  font-size: 18px;
}

/deep/ .el-form-item {
  margin-bottom: 24px;
}

/deep/ .el-form-item__error {
  font-size: 14px;
  padding-top: 2px;
}

/deep/ .el-input__suffix .el-input__clear {
  color: #848484;
  font-size: 16px;
}

/deep/ .el-input__suffix .el-input__password {
  color: #848484;
  font-size: 16px;
}

/deep/ .el-input__suffix .el-input__clear:hover {
  color: rgba(0, 0, 0, 0.88);
}

/deep/ .el-input__suffix  .el-input__password:hover {
  color: rgba(0, 0, 0, 0.88);
}

/deep/ .el-input__inner {
  color: rgba(0, 0, 0, 0.88);
}

.form-login-input {
  font-size: 16px;
}

.form-login-btn {
  width: 100%;
  font-size: 16px;
  box-shadow: 0 2px 0 rgba(5, 145, 255, 0.1);
}

.layout-footer {
  padding: 0;
  background: none;
  color: rgba(0, 0, 0, 0.88);
  font-size: 14px;
}
.global-footer {
  margin-block: 0;
  margin-block-start: 48px;
  margin-block-end: 24px;
  margin-inline: 0;
  padding-block: 0;
  padding-inline: 16px;
  text-align: center;

  color: rgba(0, 0, 0, 0.88);
  font-size: 14px;
}
.icon-copyright {
  display: inline-flex;
  align-items: center;
  color: inherit;
  font-style: normal;
  line-height: 0;
  text-align: center;
  text-transform: none;
  vertical-align: -0.125em;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>