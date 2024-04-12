import 'element-plus/dist/index.css';

import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';
import axios from "axios";
import ElementPlus from 'element-plus';
import zh_CN from 'element-plus/es/locale/lang/zh-cn';

const app = createApp(App);

axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.timeout = 15000;

app.use(ElementPlus, { locale: zh_CN });
app.use(createPinia());
app.use(router);


app.mount('#app');
