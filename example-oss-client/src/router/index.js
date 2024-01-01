import { createRouter, createWebHistory } from 'vue-router'
import {principalStore} from "@/stores/me.js";
import Layout from "@/components/layout/Index.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: () => import('@/views/WelcomeView.vue'),
      children: [
        {
          path: '',
          name: 'login',
          component: () => import('@/components/welcome/Login.vue'),
        }
      ]
    }, {
      path: '/index',
      name: 'index',
      component: Layout,
      redirect: "/home",
      children: [
        {
          path: "/home",
          name: "home",
          component: () => import("@/views/Home.vue"),
          meta: {
            title: "首页", // 标题
            icon: "HomeFilled", // 图标
            isHide: "0", // 代表路由在菜单中是否隐藏，是否隐藏（0隐藏，1显示）
            isLink: "", // 是否外链（有值则是外链）
            isKeepAlive: "0", // 是否缓存路由数据（0是，1否）
            isFull: "1", // 是否缓存全屏（0是，1否）
            isAffix: "1" // 是否缓存固定路由（0是，1否）
          }
        },
        {
          path: "/me",
          name: "me",
          component: () => import("@/views/me/me.vue"),
          meta: {
            title: "个人中心", // 标题
            icon: "UserFilled", // 图标
            isHide: "0", // 代表路由在菜单中是否隐藏，是否隐藏（0隐藏，1显示）
            isLink: "", // 是否外链（有值则是外链）
            isKeepAlive: "0", // 是否缓存路由数据（0是，1否）
            isFull: "1", // 是否缓存全屏（0是，1否）
            isAffix: "1" // 是否缓存固定路由（0是，1否）
          }
        }
      ]
      //component: () => import('@/views/IndexView.vue'),
    }
  ]
})


router.beforeEach(async (to, from) => {
  const store = principalStore();
  const isAuthenticated = !!store.principal.me;

  if (isAuthenticated && to.name === 'login') {
    return '/index';
  }

})


router.beforeEach((to, from, next) => {
  const authenticateStore = principalStore();
  const isAuthenticated = !!authenticateStore.principal.me;

  if (isAuthenticated && to.name === 'welcome') {
    next('/index');
  } else if (!isAuthenticated && to.name !== 'login') {
    next('/');
  } else if (to.matched.length === 0) {
    next('/index');
  } else {
    next();
  }
})

export default router
