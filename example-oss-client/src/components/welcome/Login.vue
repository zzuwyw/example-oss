<script setup>

import {Lock, User} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net/index.js";
import router from "@/router/index.js";
import {usePrincipalStore} from "@/stores/principal.js";

const form = reactive({
  username: '',
  password: '',
  rememberMe: false
});

const principalStore = usePrincipalStore();

const login = () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请填写用户名和密码');
  } else {
    post('/auth/login', {
      username: form.username,
      password: form.password,
      rememberMe: form.rememberMe
    }, () => {
      get('/auth/getPrincipal', (data) => {
        principalStore.principal.userDetail = data;
        router.push("/index");
      }, () => {
        principalStore.principal.userDetail = null;
      })
    })
  }
}

</script>

<template>
  <div style="text-align: center;margin: 50px;">
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
<!--      <el-divider>
        <span style="color: gray;font-size: 12px">没有账号</span>
      </el-divider>
      <div>
        <el-button type="warning" style="width: 270px">注册账号</el-button>
      </div>-->
    </div>
  </div>
</template>

<style scoped>

</style>