<script setup>

import {Lock, User} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net/index.js";
import router from "@/router/index.js";
import {principalStore} from "@/stores/me.js";

const form = reactive({
  username: '',
  password: '',
  rememberMe: false
});

const store = principalStore();

const login = () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请填写用户名和密码');
  } else {
    post('/auth/login', {
      username: form.username,
      password: form.password,
      rememberMe: form.rememberMe
    }, () => {
      get('/auth/me', (data) => {
        store.principal.me = data;
        router.push("/index");
      }, () => {
        store.principal.me = null;
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
        <el-input v-model="form.username" type="text" placeholder="用户名" :prefix-icon="User">
        </el-input>
        <el-input v-model="form.password" type="password" placeholder="密码" :prefix-icon="Lock" style="margin-top: 30px">
        </el-input>
      </div>

      <div style="margin-top: 10px">
        <el-row>
          <el-col :span="12" style="text-align: left">
            <el-checkbox v-model="form.rememberMe"><span style="font-size: 12px">记住我</span></el-checkbox>
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